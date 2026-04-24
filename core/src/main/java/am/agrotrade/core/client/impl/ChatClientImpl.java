package am.agrotrade.core.client.impl;

import am.agrotrade.common.dto.ChatDetailDto;
import am.agrotrade.common.dto.request.CreateChatRequest;
import am.agrotrade.common.dto.response.ChatResponse;
import am.agrotrade.common.enums.ChatType;
import am.agrotrade.core.client.ChatClient;
import am.agrotrade.core.exception.ChatCreationException;
import am.agrotrade.core.properties.ChatServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatClientImpl implements ChatClient {

    private final RestTemplate restTemplate;
    private final ChatServiceProperties properties;

    @Retryable(
            retryFor = {
                    ResourceAccessException.class,
                    HttpServerErrorException.class
            },
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2
            )
    )
    public ChatDetailDto createOrderChat(Long buyerId, Long sellerId, Long managerId) {

        List<Long> participants = List.of(buyerId, sellerId, managerId);
        CreateChatRequest chatRequest = new CreateChatRequest(participants, ChatType.GROUP);

        String url = UriComponentsBuilder
                .fromUriString(properties.url())
                .path(properties.createChatPath())
                .toUriString();

        HttpEntity<CreateChatRequest> entity = new HttpEntity<>(chatRequest);

        ResponseEntity<ChatResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        ChatResponse response = responseEntity.getBody();

        if (response == null || response.chatDetailDto() == null) {
            throw new ChatCreationException("Chat service returned empty response");
        }
        return response.chatDetailDto();
    }

    @Recover
    public ChatDetailDto recover(Exception ex, Long buyerId, Long sellerId, Long managerId) {
        log.error(
                "Chat creation failed after retries. buyerId=%d, sellerId=%d, managerId=%d"
                        .formatted(buyerId, sellerId, managerId),
                ex
        );
        throw new ChatCreationException(
                "Chat service is unavailable after retries: %s".formatted(ex.getMessage()),
                ex
        );
    }
}
