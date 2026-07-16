package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.ChatDocumentMessageEvent;
import am.agrotrade.common.dto.document.ChatStatusUpdateEvent;
import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDocumentIntegrationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.chat-status-update}")
    private String chatStatusUpdateTopic;

    @Value("${kafka.topics.order-document-generate}")
    private String orderDocumentGenerateTopic;

    @Value("${kafka.topics.chat-document-message}")
    private String chatDocumentMessageTopic;

    public void sendChatStatusUpdate(ChatStatusUpdateEvent event) {
        kafkaTemplate.send(chatStatusUpdateTopic, String.valueOf(event.chatId()), event);
    }

    public void sendOrderDocumentGenerate(OrderDocumentGenerateRequest request) {
        kafkaTemplate.send(orderDocumentGenerateTopic, String.valueOf(request.chatId()), request);
    }

    public void sendChatDocumentMessage(ChatDocumentMessageEvent event) {
        kafkaTemplate.send(chatDocumentMessageTopic, String.valueOf(event.chatId()), event);
    }
}
