package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.request.DocumentGenerateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentGenerationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.document-generate}")
    private String documentGenerateTopic;

    public void send(DocumentGenerateRequest request) {
        String key = request.clientInfoDto() != null ? request.clientInfoDto().fullName() : null;
        kafkaTemplate.send(documentGenerateTopic, key, request);
    }
}