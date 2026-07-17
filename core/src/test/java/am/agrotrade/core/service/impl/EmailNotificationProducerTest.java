package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.EmailNotificationEvent;
import am.agrotrade.common.dto.NotificationSettingsEvent;
import am.agrotrade.common.enums.EmailType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailNotificationProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private EmailNotificationProducer producer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(producer, "emailTopic", "email-topic");
        ReflectionTestUtils.setField(producer, "settingsTopic", "settings-topic");
    }

    @Test
    void send_emailEvent_usesEmailTypeAsKey() {
        EmailNotificationEvent event = new EmailNotificationEvent("to@mail.com", "123", null, null, EmailType.VERIFY);

        producer.send(event);

        verify(kafkaTemplate).send("email-topic", EmailType.VERIFY.name(), event);
    }

    @Test
    void send_settingsEvent_usesUserIdAsKey() {
        NotificationSettingsEvent event = NotificationSettingsEvent.builder()
                .userId(42L).email("to@mail.com").build();

        producer.send(event);

        verify(kafkaTemplate).send("settings-topic", "42", event);
    }
}
