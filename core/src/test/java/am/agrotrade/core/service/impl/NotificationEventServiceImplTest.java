package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.EmailNotificationEvent;
import am.agrotrade.common.dto.NotificationSettingsEvent;
import am.agrotrade.common.enums.EmailType;
import am.agrotrade.common.event.NotificationOrderCreatedEvent;
import am.agrotrade.common.event.UserNotificationSettingsUpdatedEvent;
import am.agrotrade.common.event.UserRegisteredEvent;
import am.agrotrade.common.event.VerificationCodeResentEvent;
import am.agrotrade.core.mapper.IntegrationEventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationEventServiceImplTest {

    @Mock
    private EmailNotificationProducer producer;
    @Mock
    private IntegrationEventMapper integrationEventMapper;

    @InjectMocks
    private NotificationEventServiceImpl service;

    private EmailNotificationEvent email() {
        return new EmailNotificationEvent("to@mail.com", "123", null, null, EmailType.VERIFY);
    }

    private NotificationSettingsEvent settings() {
        return NotificationSettingsEvent.builder().userId(1L).email("to@mail.com").build();
    }

    @Test
    void handleUserRegistered_sendsSettingsVerifyAndWelcome() {
        UserRegisteredEvent event = mock(UserRegisteredEvent.class);
        NotificationSettingsEvent settings = settings();
        EmailNotificationEvent verify = new EmailNotificationEvent("to@mail.com", "123", null, null, EmailType.VERIFY);
        EmailNotificationEvent welcome = new EmailNotificationEvent("to@mail.com", null, null, null, EmailType.WELCOME);
        when(integrationEventMapper.toSettings(event)).thenReturn(settings);
        when(integrationEventMapper.toVerify(event)).thenReturn(verify);
        when(integrationEventMapper.toWelcome(event)).thenReturn(welcome);

        service.handleUserRegistered(event);

        verify(producer).send(settings);
        verify(producer).send(verify);
        verify(producer).send(welcome);
        verify(producer, times(2)).send(any(EmailNotificationEvent.class));
    }

    @Test
    void handleVerificationCodeResent_sendsVerifyEmail() {
        VerificationCodeResentEvent event = mock(VerificationCodeResentEvent.class);
        EmailNotificationEvent verify = email();
        when(integrationEventMapper.toVerify(event)).thenReturn(verify);

        service.handleVerificationCodeResent(event);

        verify(producer).send(verify);
    }

    @Test
    void handleOrderCreated_sendsOrderEmail() {
        NotificationOrderCreatedEvent event = mock(NotificationOrderCreatedEvent.class);
        EmailNotificationEvent order = email();
        when(integrationEventMapper.toOrder(event)).thenReturn(order);

        service.handleOrderCreated(event);

        verify(producer).send(order);
    }

    @Test
    void handleUserNotificationSettingsUpdated_sendsSettingsEvent() {
        UserNotificationSettingsUpdatedEvent event = mock(UserNotificationSettingsUpdatedEvent.class);
        NotificationSettingsEvent settings = settings();
        when(integrationEventMapper.toSettingsUpdate(event)).thenReturn(settings);

        service.handleUserNotificationSettingsUpdated(event);

        verify(producer).send(settings);
    }

    private static <T> T any(Class<T> type) {
        return org.mockito.ArgumentMatchers.any(type);
    }
}
