package am.agrotrade.core.service;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.enums.EntityType;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaDto updateAvatar(long userId, MultipartFile file);

    MediaDto saveMedia(MultipartFile file, String subFolder,long entityId);

    Resource loadMediaAsResource(long entityId, EntityType entityType);

    List<MediaDto> findAllByEntity(long entityId, EntityType entityType);

}
