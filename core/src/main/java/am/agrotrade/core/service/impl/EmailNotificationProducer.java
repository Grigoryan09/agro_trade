package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.EmailNotificationEvent;
import am.agrotrade.common.dto.NotificationSettingsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.email-notification}")
    private String emailTopic;

    @Value("${kafka.topics.notification-settings}")
    private String settingsTopic;

    public void send(EmailNotificationEvent event) {
        kafkaTemplate.send(emailTopic, event.type().name(), event);
    }

    public void send(NotificationSettingsEvent event) {
        kafkaTemplate.send(settingsTopic, String.valueOf(event.getUserId()), event);
    }
}