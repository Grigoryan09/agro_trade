package am.agrotrade.core.listener;

import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.core.service.NotificationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationEventService notificationEventService;

    @EventListener
    @Async
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        notificationEventService.handleUserRegistered(event);
    }

    @EventListener
    @Async
    public void handleVerificationCodeResent(VerificationCodeResentEvent event) {
        notificationEventService.handleVerificationCodeResent(event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNotificationOrderCreatedEvent(NotificationOrderCreatedEvent event) {
        notificationEventService.handleOrderCreated(event);
    }

    @EventListener
    public void handleUserNotificationSettingsUpdated(UserNotificationSettingsUpdatedEvent event) {
        notificationEventService.handleUserNotificationSettingsUpdated(event);
    }
}