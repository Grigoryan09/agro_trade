package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.banking.event.ContractCreateRequestEvent;
import am.agrotrade.common.dto.banking.event.ContractStatusUpdateEvent;
import am.agrotrade.common.dto.document.contract.ContractDocumentGenerateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankingIntegrationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.contract-create-request}")
    private String contractCreateRequestTopic;

    @Value("${kafka.topics.contract-document-generate}")
    private String contractDocumentGenerateTopic;

    @Value("${kafka.topics.contract-status-update}")
    private String contractStatusUpdateTopic;

    public void sendContractCreateRequest(ContractCreateRequestEvent event) {
        kafkaTemplate.send(contractCreateRequestTopic, String.valueOf(event.externalRequestId()), event);
    }

    public void sendContractDocumentGenerate(ContractDocumentGenerateEvent event) {
        kafkaTemplate.send(contractDocumentGenerateTopic, String.valueOf(event.externalRequestId()), event);
    }

    public void sendContractStatusUpdate(ContractStatusUpdateEvent event) {
        kafkaTemplate.send(contractStatusUpdateTopic, String.valueOf(event.finalContractId()), event);
    }
}
