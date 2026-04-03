package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.core.mapper.MediaMapper;
import am.agrotrade.core.service.MediaService;
import am.agrotrade.web.endpoint.MediaV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MediaV1Endpoint implements MediaV1API {

    private final MediaService mediaService;
    private final MediaMapper mediaMapper;

    @Override
    public MediaResponse uploadAvatar(long userId, MultipartFile file) {
        return new MediaResponse(mediaService.updateAvatar(userId, file));
    }

    @Override
    public List<MediaResponse> uploadImages(EntityType entityType, long entityId, List<MultipartFile> files) {
        return mediaMapper.toResponseList(mediaService.saveMultipleMedia(files, entityId, entityType));
    }
}
