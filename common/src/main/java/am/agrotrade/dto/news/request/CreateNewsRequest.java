package am.agrotrade.dto.news.request;

import am.agrotrade.dto.user.response.AuthUserResponse;

import java.time.LocalDateTime;

public record CreateNewsRequest(

        String title,
        String context,
        AuthUserResponse user,
        LocalDateTime createdAt

) {
}
