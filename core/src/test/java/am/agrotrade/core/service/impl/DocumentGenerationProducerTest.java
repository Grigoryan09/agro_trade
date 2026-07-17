package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.request.DocumentGenerateRequest;
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
class DocumentGenerationProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private DocumentGenerationProducer producer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(producer, "documentGenerateTopic", "doc-generate");
    }

    @Test
    void send_nullClientInfo_sendsWithNullKey() {
        DocumentGenerateRequest request = new DocumentGenerateRequest(null, null, null, null, null, "CONTRACT");

        producer.send(request);

        verify(kafkaTemplate).send("doc-generate", null, request);
    }
}
