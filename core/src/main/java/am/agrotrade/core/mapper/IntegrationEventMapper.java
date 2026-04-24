package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.NotificationSettingsDTO;
import am.agrotrade.common.dto.request.OrderNotificationRequest;
import am.agrotrade.common.dto.request.OrderOpenedDto;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;
import am.agrotrade.common.dto.request.VerifyNotificationRequest;
import am.agrotrade.common.dto.request.WelcomeNotificationRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.core.model.User;
import am.agrotrade.core.properties.OrderServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class IntegrationEventMapper {

    private final OrderServiceProperties properties;

    public SendNotificationSettingsRequest toSettings(UserRegisteredEvent event) {
        return new SendNotificationSettingsRequest(
                NotificationSettingsDTO.builder()
                        .userId(event.userId())
                        .email(event.email())
                        .emailEnabled(event.emailEnabled())
                        .smsEnabled(event.smsEnabled())
                        .inAppEnabled(event.inAppEnabled())
                        .build()
        );
    }

    public SendNotificationSettingsRequest toSettingsUpdate(UserNotificationSettingsUpdatedEvent event) {
        return new SendNotificationSettingsRequest(
                event.notificationSettingsDTO()
        );
    }

    public VerifyNotificationRequest toVerify(UserRegisteredEvent event) {
        return new VerifyNotificationRequest(
                event.userId(),
                event.verificationCode()
        );
    }

    public OrderNotificationRequest toOrder(NotificationOrderCreatedEvent event) {
        return new OrderNotificationRequest(
                java.util.List.of(event.managerId(), event.sellerId()),
                new OrderOpenedDto(
                        event.orderUrl(),
                        event.productName()
                )
        );
    }

    public VerifyNotificationRequest toVerify(VerificationCodeResentEvent event) {
        return new VerifyNotificationRequest(
                event.userId(),
                event.verificationCode()
        );
    }

    public WelcomeNotificationRequest toWelcome(UserRegisteredEvent event) {
        return new WelcomeNotificationRequest(
                event.userId()
        );
    }

    public ChatCreatedEvent toChatCreatedEvent(
            long orderId,
            long buyerId,
            long sellerId,
            long managerId
    ) {
        return new ChatCreatedEvent(
                orderId,
                buyerId,
                sellerId,
                managerId
        );
    }

    public NotificationOrderCreatedEvent toNotificationOrderCreatedEvent(
            long orderId,
            long sellerId,
            long managerId,
            String productName
    ) {
        return new NotificationOrderCreatedEvent(
                sellerId,
                managerId,
                productName,
                buildOrderUrl(orderId)
        );
    }

    public UserRegisteredEvent toEvent(User user, RegisterRequest request) {
        return new UserRegisteredEvent(
                user.getId(),
                user.getEmail(),
                user.getVerificationCode(),
                request.emailEnabled(),
                request.smsEnabled(),
                request.inAppEnabled()
        );
    }

    public VerificationCodeResentEvent toVerificationResentEvent(User user) {
        return new VerificationCodeResentEvent(
                user.getId(),
                user.getVerificationCode()
        );
    }

    public UserNotificationSettingsUpdatedEvent toNotificationSettingsEvent(UpdateUserRequest request) {
        return new UserNotificationSettingsUpdatedEvent(
                request.notificationSettingsDTO()
        );
    }

    private String buildOrderUrl(long orderId) {
        return UriComponentsBuilder
                .fromUriString(properties.baseUrl())
                .path(properties.orderPath())
                .pathSegment(String.valueOf(orderId))
                .toUriString();
    }

}
