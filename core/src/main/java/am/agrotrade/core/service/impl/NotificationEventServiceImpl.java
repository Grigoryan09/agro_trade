package am.agrotrade.core.service.impl;

import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.core.mapper.IntegrationEventMapper;
import am.agrotrade.core.service.NotificationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventServiceImpl implements NotificationEventService {

    private final EmailNotificationProducer producer;
    private final IntegrationEventMapper integrationEventMapper;


    @Override
    public void handleUserRegistered(UserRegisteredEvent event) {
        producer.send(integrationEventMapper.toSettings(event));    // toSettingsEvent չէ
        producer.send(integrationEventMapper.toVerify(event));      // toVerifyEvent չէ
        producer.send(integrationEventMapper.toWelcome(event));
    }

    @Override
    public void handleVerificationCodeResent(VerificationCodeResentEvent event) {
        producer.send(integrationEventMapper.toVerify(event));      // toVerifyEvent չէ
    }

    @Override
    public void handleOrderCreated(NotificationOrderCreatedEvent event) {
        producer.send(integrationEventMapper.toOrder(event));       // toOrderEvent չէ
    }

    @Override
    public void handleUserNotificationSettingsUpdated(UserNotificationSettingsUpdatedEvent event) {
        producer.send(integrationEventMapper.toSettingsUpdate(event)); // toSettingsEvent չէ
    }


}
