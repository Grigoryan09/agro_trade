package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.common.enums.EntityType;
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

    @Override
    public MediaResponse uploadImages(EntityType entityType, long entityId, List<MultipartFile> files) {
        return new MediaResponse(mediaService.saveMultipleMedia(files, entityId, entityType));
    }
}
