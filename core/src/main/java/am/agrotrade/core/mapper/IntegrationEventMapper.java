package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.EmailNotificationEvent;
import am.agrotrade.common.dto.NotificationSettingsEvent;
import am.agrotrade.common.enums.EmailType;
import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.core.model.User;
import am.agrotrade.core.properties.OrderServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class IntegrationEventMapper {

    private final OrderServiceProperties properties;

    public NotificationSettingsEvent toSettings(UserRegisteredEvent event) {
        return new NotificationSettingsEvent(
                event.userId(),
                event.email(),
                event.emailEnabled(),
                event.smsEnabled(),
                event.inAppEnabled()
        );
    }

    public NotificationSettingsEvent toSettingsUpdate(UserNotificationSettingsUpdatedEvent event) {
        NotificationSettingsEvent dto = event.notificationSettingsEvent();
        return new NotificationSettingsEvent(
                dto.getUserId(),
                dto.getEmail(),
                dto.getEmailEnabled(),
                dto.getSmsEnabled(),
                dto.getInAppEnabled()
        );
    }

    public EmailNotificationEvent toVerify(UserRegisteredEvent event) {
        return new EmailNotificationEvent(
                event.email(),
                event.verificationCode(),
                null,
                null,
                EmailType.VERIFY
        );
    }

    public EmailNotificationEvent toVerify(VerificationCodeResentEvent event) {
        return new EmailNotificationEvent(
                event.email(),
                event.verificationCode(),
                null,
                null,
                EmailType.VERIFY
        );
    }

    public EmailNotificationEvent toOrder(NotificationOrderCreatedEvent event) {
        return new EmailNotificationEvent(
                null,
                null,
                event.orderUrl(),
                event.productName(),
                EmailType.ORDER_OPENED
        );
    }

    public EmailNotificationEvent toWelcome(UserRegisteredEvent event) {
        return new EmailNotificationEvent(
                event.email(),
                null,
                null,
                null,
                EmailType.WELCOME
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
                user.getVerificationCode(),
                user.getEmail()
        );
    }

    public UserNotificationSettingsUpdatedEvent toNotificationSettingsEvent(UpdateUserRequest request) {
        return new UserNotificationSettingsUpdatedEvent(
                request.notificationSettingsEvent()
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
