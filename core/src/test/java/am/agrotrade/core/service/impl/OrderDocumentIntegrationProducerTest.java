package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.ChatDocumentMessageEvent;
import am.agrotrade.common.dto.document.ChatStatusUpdateEvent;
import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
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
class OrderDocumentIntegrationProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private OrderDocumentIntegrationProducer producer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(producer, "chatStatusUpdateTopic", "chat-status");
        ReflectionTestUtils.setField(producer, "orderDocumentGenerateTopic", "order-doc-generate");
        ReflectionTestUtils.setField(producer, "chatDocumentMessageTopic", "chat-doc-message");
    }

    @Test
    void sendChatStatusUpdate_usesChatIdAsKey() {
        ChatStatusUpdateEvent event = new ChatStatusUpdateEvent(5L, "COMPLETED");

        producer.sendChatStatusUpdate(event);

        verify(kafkaTemplate).send("chat-status", "5", event);
    }

    @Test
    void sendOrderDocumentGenerate_usesChatIdAsKey() {
        OrderDocumentGenerateRequest request = new OrderDocumentGenerateRequest(
                1L, "2026-07-14", null, null, null, null, 2, "20.00", 7L, 3L);

        producer.sendOrderDocumentGenerate(request);

        verify(kafkaTemplate).send("order-doc-generate", "7", request);
    }

    @Test
    void sendChatDocumentMessage_usesChatIdAsKey() {
        ChatDocumentMessageEvent event = new ChatDocumentMessageEvent(9L, 3L, "file.docx", "http://url");

        producer.sendChatDocumentMessage(event);

        verify(kafkaTemplate).send("chat-doc-message", "9", event);
    }
}
