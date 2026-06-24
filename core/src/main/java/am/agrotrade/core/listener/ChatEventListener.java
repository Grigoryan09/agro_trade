package am.agrotrade.core.listener;

import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.core.service.ChatEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatEventListener {

    private final ChatEventService chatEventService;

    @EventListener
    public void handleChatCreatedEvent(ChatCreatedEvent event) {
        chatEventService.createChatForOrder(event);
    }
}