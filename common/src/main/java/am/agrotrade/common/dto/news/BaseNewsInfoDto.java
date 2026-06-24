package am.agrotrade.common.dto.news;

import am.agrotrade.common.dto.user.BaseUserInfoDto;

import java.time.LocalDateTime;

public record BaseNewsInfoDto(

        String title,
        String context,
        BaseUserInfoDto user,
        LocalDateTime createdAt
) {
}
