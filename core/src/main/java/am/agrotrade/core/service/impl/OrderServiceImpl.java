package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.BuyerInfoDto;
import am.agrotrade.common.dto.document.ChatStatusUpdateEvent;
import am.agrotrade.common.dto.document.ManagerInfoDto;
import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
import am.agrotrade.common.dto.document.SellerInfoDto;
import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.product.ProductDetailsDto;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.exception.InvalidOrderDataException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.IntegrationEventMapper;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final IntegrationEventMapper integrationEventMapper;
    private final OrderDocumentIntegrationProducer orderDocumentIntegrationProducer;

    @Override
    @Transactional
    public OrderDetailsDto save(long buyerId, CreateOrderRequest request) {

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));

        User seller = userRepository.findById(request.sellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User manager = userRepository.findCandidatesForUpdate(
                        Role.MANAGER,
                        PageRequest.of(0, 1)
                )
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No manager found"));

        userRepository.incrementOrders(manager.getId());

        BigDecimal totalPrice = calculateTotalPrice(request.price(), request.quantity());

        Order order = orderMapper.toEntity(
                buyer, seller, manager, product,
                request.quantity(), totalPrice
        );

        Order savedOrder = orderRepository.save(order);

        eventPublisher.publishEvent(integrationEventMapper.toChatCreatedEvent(
                        savedOrder.getId(),
                        buyer.getId(),
                        seller.getId(),
                        manager.getId()
                )
        );

        eventPublisher.publishEvent(integrationEventMapper.toNotificationOrderCreatedEvent(
                savedOrder.getId(),
                seller.getId(),
                manager.getId(),
                product.getName()
                )
        );

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDetailsDto updateStatus(long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));
        orderMapper.updateOrderFromRequest(request, order);

        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            orderDocumentIntegrationProducer.sendChatStatusUpdate(
                    new ChatStatusUpdateEvent(order.getChatId(), OrderStatus.COMPLETED.name())
            );
        }

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void attachChatToOrder(long orderId, long chatId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));
        order.setChatId(chatId);
        order.setOrderStatus(OrderStatus.PROCESSING);
    }

    @Override
    @Transactional
    public void delete(long managerId, long orderId) {
        Order order = orderRepository.findByIdAndManagerId(orderId, managerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for ID: %d ".formatted(orderId)
                ));
        order.setOrderStatus(OrderStatus.DELETED);
    }

    @Override
    public List<OrderDetailsDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDto)
                .toList();
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
    public List<OrderDetailsDto> findByManagerId(long managerId, Pageable pageable) {
        return orderRepository.findAllByManagerId(managerId, pageable)
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDocumentGenerateRequest buildOrderDocumentRequest(long chatId) {
        Order order = orderRepository.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for chat ID: %d ".formatted(chatId)
                ));

        User buyer = order.getBuyer();
        User seller = order.getSeller();
        User manager = order.getManager();
        Product product = order.getProduct();

        return new OrderDocumentGenerateRequest(
                order.getId(),
                LocalDate.now().toString(),
                new BuyerInfoDto(fullName(buyer), buyer.getAddress(), buyer.getEmail(), buyer.getPhoneNumber()),
                new SellerInfoDto(fullName(seller), seller.getAddress(), seller.getEmail(), seller.getPhoneNumber()),
                new ManagerInfoDto(fullName(manager), manager.getAddress(), manager.getEmail(), manager.getPhoneNumber()),
                new ProductDetailsDto(product.getName(),
                        product.getCategory() != null ? product.getCategory().name() : ""),
                order.getQuantity(),
                order.getTotalPrice() != null ? order.getTotalPrice().toPlainString() : "",
                order.getChatId(),
                manager.getId()
        );
    }

    private String fullName(User user) {
        return "%s %s".formatted(safe(user.getName()), safe(user.getSurname())).trim();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private BigDecimal calculateTotalPrice(BigDecimal price, long quantity) {
        if (quantity <= 0) {
            throw new InvalidOrderDataException("Quantity must be greater than 0");
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
