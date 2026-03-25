package am.agrotrade.common.dto.news.response;

import am.agrotrade.common.dto.user.BaseUserInfoDto;

import java.time.LocalDateTime;

public record BaseNewsInfoDto(

        String title,
        String context,
        BaseUserInfoDto user,
        LocalDateTime createAt

) {
}
