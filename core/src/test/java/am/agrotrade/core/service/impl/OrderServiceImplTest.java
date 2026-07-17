package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.ChatStatusUpdateEvent;
import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final long BUYER_ID = 1L;
    private static final long SELLER_ID = 2L;
    private static final long PRODUCT_ID = 3L;
    private static final long MANAGER_ID = 4L;
    private static final long ORDER_ID = 100L;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private IntegrationEventMapper integrationEventMapper;
    @Mock
    private OrderDocumentIntegrationProducer orderDocumentIntegrationProducer;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User buyer;
    private User seller;
    private User manager;
    private Product product;
    private OrderDetailsDto dto;

    @BeforeEach
    void setUp() {
        buyer = user(BUYER_ID);
        seller = user(SELLER_ID);
        manager = user(MANAGER_ID);
        product = new Product();
        product.setId(PRODUCT_ID);
        product.setName("Wheat");
        dto = new OrderDetailsDto(ORDER_ID, null, null, null, null, 0L, null, null, 0L);
    }

    private static User user(long id) {
        User u = new User();
        u.setId(id);
        u.setName("N" + id);
        u.setSurname("S" + id);
        u.setEmail("u" + id + "@mail.com");
        u.setAddress("addr" + id);
        u.setPhoneNumber("+3741234567" + id);
        return u;
    }

    private CreateOrderRequest createRequest(long quantity) {
        return new CreateOrderRequest(SELLER_ID, PRODUCT_ID, quantity, new BigDecimal("10.00"));
    }

    private void stubAllLookups() {
        when(userRepository.findById(BUYER_ID)).thenReturn(Optional.of(buyer));
        when(userRepository.findById(SELLER_ID)).thenReturn(Optional.of(seller));
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(userRepository.findCandidatesForUpdate(eq(Role.MANAGER), any(Pageable.class)))
                .thenReturn(List.of(manager));
    }

    @Test
    void save_happyPath_incrementsManager_savesOrder_andPublishesTwoEvents() {
        stubAllLookups();
        Order mapped = new Order();
        Order saved = new Order();
        saved.setId(ORDER_ID);
        when(orderMapper.toEntity(eq(buyer), eq(seller), eq(manager), eq(product), eq(5L), any(BigDecimal.class)))
                .thenReturn(mapped);
        when(orderRepository.save(mapped)).thenReturn(saved);
        when(orderMapper.toDto(saved)).thenReturn(dto);

        OrderDetailsDto result = orderService.save(BUYER_ID, createRequest(5));

        assertThat(result).isSameAs(dto);
        verify(userRepository).incrementOrders(MANAGER_ID);
        // total price = 10.00 * 5
        ArgumentCaptor<BigDecimal> price = ArgumentCaptor.forClass(BigDecimal.class);
        verify(orderMapper).toEntity(any(), any(), any(), any(), eq(5L), price.capture());
        assertThat(price.getValue()).isEqualByComparingTo("50.00");
        verify(eventPublisher, times(2)).publishEvent(nullable(Object.class));
    }

    @Test
    void save_buyerNotFound_throws() {
        when(userRepository.findById(BUYER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.save(BUYER_ID, createRequest(5)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Buyer not found");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void save_noManagerAvailable_throws() {
        when(userRepository.findById(BUYER_ID)).thenReturn(Optional.of(buyer));
        when(userRepository.findById(SELLER_ID)).thenReturn(Optional.of(seller));
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        when(userRepository.findCandidatesForUpdate(eq(Role.MANAGER), any(Pageable.class)))
                .thenReturn(List.of());

        assertThatThrownBy(() -> orderService.save(BUYER_ID, createRequest(5)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No manager found");
    }

    @Test
    void save_nonPositiveQuantity_throwsInvalidOrderData() {
        stubAllLookups();

        assertThatThrownBy(() -> orderService.save(BUYER_ID, createRequest(0)))
                .isInstanceOf(InvalidOrderDataException.class)
                .hasMessage("Quantity must be greater than 0");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void updateStatus_completed_sendsChatStatusUpdate() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setChatId(55L);
        order.setOrderStatus(OrderStatus.COMPLETED);
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(dto);

        orderService.updateStatus(ORDER_ID, new UpdateOrderStatusRequest(OrderStatus.COMPLETED));

        ArgumentCaptor<ChatStatusUpdateEvent> captor = ArgumentCaptor.forClass(ChatStatusUpdateEvent.class);
        verify(orderDocumentIntegrationProducer).sendChatStatusUpdate(captor.capture());
        assertThat(captor.getValue().chatId()).isEqualTo(55L);
        assertThat(captor.getValue().status()).isEqualTo(OrderStatus.COMPLETED.name());
    }

    @Test
    void updateStatus_notCompleted_doesNotNotifyChat() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PROCESSING);
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(dto);

        orderService.updateStatus(ORDER_ID, new UpdateOrderStatusRequest(OrderStatus.PROCESSING));

        verify(orderDocumentIntegrationProducer, never()).sendChatStatusUpdate(any());
    }

    @Test
    void updateStatus_orderNotFound_throws() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateStatus(ORDER_ID, new UpdateOrderStatusRequest(OrderStatus.PROCESSING)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void attachChatToOrder_setsChatIdAndProcessingStatus() {
        Order order = new Order();
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));

        orderService.attachChatToOrder(ORDER_ID, 77L);

        assertThat(order.getChatId()).isEqualTo(77L);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    void delete_softDeletesWhenOwnedByManager() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PROCESSING);
        when(orderRepository.findByIdAndManagerId(ORDER_ID, MANAGER_ID)).thenReturn(Optional.of(order));

        orderService.delete(MANAGER_ID, ORDER_ID);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.DELETED);
    }

    @Test
    void delete_notOwned_throws() {
        when(orderRepository.findByIdAndManagerId(ORDER_ID, MANAGER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.delete(MANAGER_ID, ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findById_notFound_throws() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.findById(ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void buildOrderDocumentRequest_mapsOrderParticipants() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setManager(manager);
        order.setProduct(product);
        order.setQuantity(3);
        order.setTotalPrice(new BigDecimal("30.00"));
        order.setChatId(9L);
        when(orderRepository.findByChatId(9L)).thenReturn(Optional.of(order));

        OrderDocumentGenerateRequest req = orderService.buildOrderDocumentRequest(9L);

        assertThat(req.orderId()).isEqualTo(ORDER_ID);
        assertThat(req.chatId()).isEqualTo(9L);
        assertThat(req.senderUserId()).isEqualTo(MANAGER_ID);
        assertThat(req.buyerInfoDto().fullName()).isEqualTo("N1 S1");
        assertThat(req.totalPrice()).isEqualTo("30.00");
    }

    @Test
    void buildOrderDocumentRequest_notFound_throws() {
        when(orderRepository.findByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.buildOrderDocumentRequest(9L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
