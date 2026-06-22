package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.EmailNotificationEvent;
import am.agrotrade.common.dto.NotificationSettingsEvent;
import am.agrotrade.core.service.impl.EmailNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/kafka")
@RequiredArgsConstructor
public class KafkaTestEndpoint {

    private final EmailNotificationProducer producer;

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailNotificationEvent event) {
        producer.send(event);
    }

    @PostMapping("/send-settings")
    public void sendSettings(@RequestBody NotificationSettingsEvent event) {
        producer.send(event);
    }
}