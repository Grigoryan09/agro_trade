package am.agrotrade.core.listener;

import am.agrotrade.common.dto.ChatDetailDto;
import am.agrotrade.common.dto.NotificationSettingsDTO;
import am.agrotrade.common.dto.OrderOpenedDto;
import am.agrotrade.common.dto.request.SendNotificationRequest;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;
import am.agrotrade.common.enums.EmailType;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.core.client.ChatClient;
import am.agrotrade.core.client.NotificationClient;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.model.Order;
import am.agrotrade.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceEventListener {

    private final ChatClient chatClient;
    private final NotificationClient notificationClient;
    private final OrderRepository orderRepository;

    @EventListener
    public void handle(ChatCreatedEvent event) {

        ChatDetailDto chat = chatClient.createOrderChat(
                event.buyerId(),
                event.sellerId(),
                event.managerId()
        );

        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setChatId(chat.getId());
        order.setOrderStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);
    }

    @EventListener
    @Async
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        notificationClient.sendNotificationSettings(new SendNotificationSettingsRequest(
                NotificationSettingsDTO.builder()
                        .userId(event.userId())
                        .email(event.email())
                        .emailEnabled(event.emailEnabled())
                        .smsEnabled(event.smsEnabled())
                        .inAppEnabled(event.inAppEnabled())
                        .build()
        ));
        notificationClient.sendNotification(SendNotificationRequest.builder()
                .userIds(List.of(event.userId()))
                .code(event.verificationCode())
                .emailType(EmailType.VERIFY)
                .build());
    }

    @EventListener
    @Async
    public void handleVerificationCodeResent(VerificationCodeResentEvent event) {
        notificationClient.sendNotification(SendNotificationRequest.builder()
                .userIds(List.of(event.userId()))
                .code(event.verificationCode())
                .emailType(EmailType.VERIFY)
                .build());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotification(NotificationOrderCreatedEvent event) {
        notificationClient.sendNotification(SendNotificationRequest.builder()
                .userIds(List.of(event.managerId(), event.sellerId()))
                .emailType(EmailType.ORDER_OPENED)
                .orderOpenedDto(OrderOpenedDto.builder()
                        .orderUrl(event.orderUrl())
                        .productName(event.productName())
                        .build())
                .build()
        );
    }
}
