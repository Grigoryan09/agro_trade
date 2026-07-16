package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.ChatDocumentMessageEvent;
import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
import am.agrotrade.common.dto.document.OrderDocumentRequestEvent;
import am.agrotrade.common.dto.document.OrderDocumentResultEvent;
import am.agrotrade.common.dto.document.request.CreateDocumentDto;
import am.agrotrade.common.enums.DocumentFormat;
import am.agrotrade.common.enums.DocumentType;
import am.agrotrade.core.service.DocumentService;
import am.agrotrade.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderDocumentConsumer {

    private final OrderService orderService;
    private final DocumentService documentService;
    private final OrderDocumentIntegrationProducer orderDocumentIntegrationProducer;

    @KafkaListener(
            topics = "${kafka.topics.order-document-request}",
            containerFactory = "orderDocumentRequestKafkaListenerContainerFactory")
    public void onOrderDocumentRequest(OrderDocumentRequestEvent event) {
        log.info("Received order document request for chat {}", event.chatId());

        OrderDocumentGenerateRequest request = orderService.buildOrderDocumentRequest(event.chatId());
        orderDocumentIntegrationProducer.sendOrderDocumentGenerate(request);
    }

    @KafkaListener(
            topics = "${kafka.topics.order-document-result}",
            containerFactory = "orderDocumentResultKafkaListenerContainerFactory")
    public void onOrderDocumentResult(OrderDocumentResultEvent event) {
        log.info("Received generated order document for chat {}: {}", event.chatId(), event.fileName());

        String downloadUrl = documentService.save(new CreateDocumentDto(
                DocumentType.ORDER,
                event.fileName(),
                DocumentFormat.DOCX,
                null,
                LocalDateTime.now(),
                event.base64Document()
        ));

        orderDocumentIntegrationProducer.sendChatDocumentMessage(new ChatDocumentMessageEvent(
                event.chatId(),
                event.senderUserId(),
                event.fileName(),
                downloadUrl
        ));
    }
}
