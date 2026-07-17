package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.ChatDetailDto;
import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.core.client.ChatClient;
import am.agrotrade.core.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatEventServiceImplTest {

    @Mock
    private OrderService orderService;
    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private ChatEventServiceImpl chatEventService;

    @Test
    void createChatForOrder_createsChatThenAttachesToOrder() {
        ChatCreatedEvent event = new ChatCreatedEvent(10L, 1L, 2L, 3L);
        ChatDetailDto chat = ChatDetailDto.builder().id(99L).build();
        when(chatClient.createOrderChat(1L, 2L, 3L)).thenReturn(chat);

        chatEventService.createChatForOrder(event);

        verify(chatClient).createOrderChat(1L, 2L, 3L);
        verify(orderService).attachChatToOrder(10L, 99L);
    }
}
