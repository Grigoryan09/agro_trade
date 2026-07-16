package am.agrotrade.common.dto.news;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.user.BaseUserInfoDto;

import java.time.LocalDateTime;
import java.util.List;

public record BaseNewsInfoDto(

        Long id,
        String title,
        String context,
        BaseUserInfoDto user,
        LocalDateTime createdAt,
        List<MediaDto> media
) {
    public BaseNewsInfoDto withMedia(List<MediaDto> media) {
        return new BaseNewsInfoDto(id, title, context, user, createdAt, media);
    }
}
