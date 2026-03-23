package am.agrotrade.dto.media.request;

import java.time.LocalDateTime;

public record CreateMediaRequest(

        String url,
        String filePath,
        String entityType,
        long entityId,
        LocalDateTime createdAt

){
}
