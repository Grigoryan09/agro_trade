package am.agrotrade.core.service.impl;

import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.core.client.NotificationClient;
import am.agrotrade.core.mapper.IntegrationEventMapper;
import am.agrotrade.core.service.NotificationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventServiceImpl implements NotificationEventService {

    private final NotificationClient notificationClient;
    private final IntegrationEventMapper integrationEventMapper;

    @Override
    public void handleUserRegistered(UserRegisteredEvent event) {
        notificationClient.sendNotificationSettings(
                integrationEventMapper.toSettings(event)
        );

        notificationClient.sendVerifyEmail(
                integrationEventMapper.toVerify(event));
    }

    @Override
    public void handleVerificationCodeResent(VerificationCodeResentEvent event) {
        notificationClient.sendVerifyEmail(integrationEventMapper.toVerify(event));
    }

    @Override
    public void handleOrderCreated(NotificationOrderCreatedEvent event) {
        notificationClient.sendOrderOpenedEmail(
                integrationEventMapper.toOrder(event)
        );
    }

    @Override
    public void handleUserNotificationSettingsUpdated(UserNotificationSettingsUpdatedEvent event) {
        notificationClient.sendNotificationSettings(
                integrationEventMapper.toSettingsUpdate(event)
        );
    }
}
