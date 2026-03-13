package am.agrotrade.dto.news.response;

import am.agrotrade.dto.user.response.BaseUserInfoDto;

import java.time.LocalDateTime;

public record BaseNewsInfoDto(

        String title,
        String context,
        BaseUserInfoDto user,
        LocalDateTime createAt

) {
}
