package am.agrotrade.core.service;

import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;

/**
 * Handles notification-related domain events.
 */
public interface NotificationEventService {

    /**
     * Processes the event emitted after a user registration is completed.
     *
     * @param event registration event payload
     */
    void handleUserRegistered(UserRegisteredEvent event);

    /**
     * Processes the event emitted after a verification code is resent.
     *
     * @param event verification code resend event payload
     */
    void handleVerificationCodeResent(VerificationCodeResentEvent event);

    /**
     * Processes the event emitted after an order creation notification is triggered.
     *
     * @param event order creation notification event payload
     */
    void handleOrderCreated(NotificationOrderCreatedEvent event);

    /**
     * Processes the event emitted after a user updates notification settings.
     *
     * @param event notification settings update event payload
     */
    void handleUserNotificationSettingsUpdated(UserNotificationSettingsUpdatedEvent event);
}
