package am.agrotrade.core.client;

import am.agrotrade.common.dto.ChatDetailDto;

public interface ChatClient {

    ChatDetailDto createOrderChat(Long buyerId, Long sellerId, Long managerId);

}