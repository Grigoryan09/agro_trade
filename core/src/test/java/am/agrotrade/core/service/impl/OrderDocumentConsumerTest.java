package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.ChatDocumentMessageEvent;
import am.agrotrade.common.dto.document.OrderDocumentGenerateRequest;
import am.agrotrade.common.dto.document.OrderDocumentRequestEvent;
import am.agrotrade.common.dto.document.OrderDocumentResultEvent;
import am.agrotrade.common.dto.document.request.CreateDocumentDto;
import am.agrotrade.common.enums.DocumentType;
import am.agrotrade.core.service.DocumentService;
import am.agrotrade.core.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDocumentConsumerTest {

    @Mock
    private OrderService orderService;
    @Mock
    private DocumentService documentService;
    @Mock
    private OrderDocumentIntegrationProducer orderDocumentIntegrationProducer;

    @InjectMocks
    private OrderDocumentConsumer consumer;

    @Test
    void onOrderDocumentRequest_buildsRequestAndForwardsToProducer() {
        OrderDocumentGenerateRequest request = new OrderDocumentGenerateRequest(
                1L, "2026-07-14", null, null, null, null, 2, "20.00", 5L, 3L);
        when(orderService.buildOrderDocumentRequest(5L)).thenReturn(request);

        consumer.onOrderDocumentRequest(new OrderDocumentRequestEvent(5L));

        verify(orderDocumentIntegrationProducer).sendOrderDocumentGenerate(request);
    }

    @Test
    void onOrderDocumentResult_savesDocumentAndSendsChatMessage() {
        OrderDocumentResultEvent event = new OrderDocumentResultEvent(9L, 3L, "contract.docx", "BASE64");
        when(documentService.save(any(CreateDocumentDto.class))).thenReturn("http://download/url");

        consumer.onOrderDocumentResult(event);

        ArgumentCaptor<CreateDocumentDto> docCaptor = ArgumentCaptor.forClass(CreateDocumentDto.class);
        verify(documentService).save(docCaptor.capture());
        assertThat(docCaptor.getValue().type()).isEqualTo(DocumentType.ORDER);
        assertThat(docCaptor.getValue().name()).isEqualTo("contract.docx");
        assertThat(docCaptor.getValue().base64Content()).isEqualTo("BASE64");

        ArgumentCaptor<ChatDocumentMessageEvent> msgCaptor = ArgumentCaptor.forClass(ChatDocumentMessageEvent.class);
        verify(orderDocumentIntegrationProducer).sendChatDocumentMessage(msgCaptor.capture());
        assertThat(msgCaptor.getValue().chatId()).isEqualTo(9L);
        assertThat(msgCaptor.getValue().fileUrl()).isEqualTo("http://download/url");
    }
}
