package am.agrotrade.common.dto;

import java.time.LocalDateTime;

public record ChatMessageDto(

        long id,
        long userId,
        String message,
        String status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate) {
}


