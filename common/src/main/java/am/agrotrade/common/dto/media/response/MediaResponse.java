package am.agrotrade.common.dto.media.response;

import am.agrotrade.common.dto.media.MediaDto;

import java.util.List;

public record MediaResponse(List<MediaDto> mediaDtoList) {
}
