package am.agrotrade.core.listener;

import am.agrotrade.common.event.ChatCreatedEvent;
import am.agrotrade.core.service.ChatEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ChatEventListener {

    private final ChatEventService chatEventService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleChatCreatedEvent(ChatCreatedEvent event) {
        chatEventService.createChatForOrder(event);
    }
}