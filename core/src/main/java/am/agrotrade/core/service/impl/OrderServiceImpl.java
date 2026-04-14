package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.ChatDetailDto;
import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.request.CreateChatRequest;
import am.agrotrade.common.dto.response.ChatResponse;
import am.agrotrade.common.enums.ChatType;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.core.exception.ChatCreationException;
import am.agrotrade.core.exception.InvalidOrderDataException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.OrderMapper;
import am.agrotrade.core.model.Order;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.OrderRepository;
import am.agrotrade.core.repository.ProductRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    @Value("${application.config.chat-service-url}")
    private String chatServiceUrl;

    @Override
    @Transactional
    public OrderDetailsDto save(long buyerId, CreateOrderRequest request) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
        User seller = userRepository.findById(request.sellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User manager = userRepository.findById(
                        userRepository.findFreeManagerForUpdate("MANAGER"))
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        BigDecimal totalPrice = calculateTotalPrice(request.price(), request.quantity());

        Order order = createOrderEntity(buyer,
                seller,
                manager,
                product,
                request.quantity(),
                totalPrice);

        ChatDetailDto chatDetailDto = createOrderChat(buyer.getId(), seller.getId(), manager.getId());

        order.setChatId(chatDetailDto.getId());
        Order savedOrder = orderRepository.save(order);

        return new OrderDetailsDto(
                orderMapper.toBuyerDto(savedOrder.getBuyer()),
                orderMapper.toSellerDto(savedOrder.getSeller()),
                orderMapper.toManagerDto(savedOrder.getManager()),
                orderMapper.toProductDto(savedOrder.getProduct()),
                chatDetailDto,
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getOrderStatus()
        );
    }

    @Override
    @Transactional
    public OrderDetailsDto updateStatus(long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));
        orderMapper.updateOrderFromRequest(request, order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void delete(long managerId, long orderId) {
        Order order = orderRepository.findByManagerIdAndOrderId(managerId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));

        order.setOrderStatus(OrderStatus.DELETED);
    }

    @Override
    public Page<OrderDetailsDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).
                map(orderMapper::toDto);
    }

    @Override
    public OrderDetailsDto findById(long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));
    }

    @Override
    public Page<OrderDetailsDto> findByManagerId(long managerId, Pageable pageable) {
        return orderRepository.findAllByManagerId(managerId, pageable)
                .map(orderMapper::toDto);
    }

    private BigDecimal calculateTotalPrice(BigDecimal price, long quantity) {

        if (price == null) {
            throw new InvalidOrderDataException("Price cannot be null");
        }

        if (quantity <= 0) {
            throw new InvalidOrderDataException("Quantity must be greater than 0");
        }

        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private Order createOrderEntity(User buyer, User seller, User manager, Product product, long quantity, BigDecimal totalPrice) {
        Order order = new Order();
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setManager(manager);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.PENDING);
        return order;
    }

    private ChatDetailDto createOrderChat(Long buyerId, Long sellerId, Long managerId) {
        List<Long> participants = List.of(buyerId, sellerId, managerId);
        CreateChatRequest chatRequest = new CreateChatRequest(participants, ChatType.GROUP);

        String url = chatServiceUrl + "/chat-service/api/v1/chats";
        ChatResponse response = restTemplate.postForObject(url, chatRequest, ChatResponse.class);

        if (response == null || response.chatDetailDto() == null) {
            throw new ChatCreationException("Chat service returned empty response");
        }

        return response.chatDetailDto();

    }
}
