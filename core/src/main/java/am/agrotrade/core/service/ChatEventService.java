package am.agrotrade.core.service;

import am.agrotrade.common.event.ChatCreatedEvent;

/**
 * Handles chat-related domain events.
 */
public interface ChatEventService {

    /**
     * Processes the event emitted when a chat should be created for an order.
     *
     * @param event chat creation event payload
     */
    void createChatForOrder(ChatCreatedEvent event);

}
