package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.banking.event.ContractCreateResultEvent;
import am.agrotrade.common.dto.banking.event.ContractStatusUpdateEvent;
import am.agrotrade.common.dto.document.contract.ContractDocumentGenerateEvent;
import am.agrotrade.common.dto.document.contract.ContractDocumentResultEvent;
import am.agrotrade.common.dto.document.request.CreateDocumentDto;
import am.agrotrade.common.enums.DocumentFormat;
import am.agrotrade.common.enums.DocumentType;
import am.agrotrade.core.service.BankingRequestService;
import am.agrotrade.core.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankingContractConsumer {

    private final BankingRequestService bankingRequestService;
    private final DocumentService documentService;
    private final BankingIntegrationProducer bankingIntegrationProducer;

    @KafkaListener(
            topics = "${kafka.topics.contract-create-result}",
            containerFactory = "contractCreateResultKafkaListenerContainerFactory")
    public void onContractCreateResult(ContractCreateResultEvent event) {
        log.info("Received final contract {} for banking request {}",
                event.finalContractId(), event.externalRequestId());

        ContractDocumentGenerateEvent generateEvent = bankingRequestService.onContractCreated(event);
        bankingIntegrationProducer.sendContractDocumentGenerate(generateEvent);
    }

    @KafkaListener(
            topics = "${kafka.topics.contract-document-result}",
            containerFactory = "contractDocumentResultKafkaListenerContainerFactory")
    public void onContractDocumentResult(ContractDocumentResultEvent event) {
        log.info("Received generated contract document for banking request {}: {}",
                event.externalRequestId(), event.fileName());

        String downloadUrl = documentService.save(new CreateDocumentDto(
                DocumentType.CONTRACT,
                event.fileName(),
                DocumentFormat.DOCX,
                null,
                LocalDateTime.now(),
                event.base64Document()
        ));

        bankingRequestService.completeContract(event.externalRequestId());

        bankingIntegrationProducer.sendContractStatusUpdate(
                new ContractStatusUpdateEvent(event.finalContractId(), downloadUrl));
    }
}
