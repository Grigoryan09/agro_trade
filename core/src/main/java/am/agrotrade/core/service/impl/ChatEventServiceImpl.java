package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.ChatDetailDto;
import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.core.client.ChatClient;
import am.agrotrade.core.service.ChatEventService;
import am.agrotrade.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatEventServiceImpl implements ChatEventService {

    private final OrderService orderService;
    private final ChatClient chatClient;

    @Override
    public void createChatForOrder(ChatCreatedEvent event) {
        ChatDetailDto chat = chatClient.createOrderChat(
                event.buyerId(),
                event.sellerId(),
                event.managerId()
        );

        orderService.attachChatToOrder(
                event.orderId(),
                chat.getId()
        );
    }
}
