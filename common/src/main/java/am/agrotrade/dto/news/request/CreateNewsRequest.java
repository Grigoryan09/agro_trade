package am.agrotrade.dto.news.request;

import java.time.LocalDateTime;

public record CreateNewsRequest(

        String title,
        String context,
        LocalDateTime createdAt

) {
}
