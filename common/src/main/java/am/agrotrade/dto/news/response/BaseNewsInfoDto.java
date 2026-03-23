package am.agrotrade.dto.news.response;

import am.agrotrade.dto.user.BaseUserInfoDto;

import java.time.LocalDateTime;

public record BaseNewsInfoDto(

        String title,
        String context,
        BaseUserInfoDto user,
        LocalDateTime createAt

) {
}
