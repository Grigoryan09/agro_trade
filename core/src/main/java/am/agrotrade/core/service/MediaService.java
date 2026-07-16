package am.agrotrade.core.service;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.enums.EntityType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Handles media storage and retrieval for domain entities.
 */
public interface MediaService {

    /**
     * Stores multiple files for the specified entity.
     *
     * @param files files to store
     * @param entityId target entity identifier
     * @param entityType target entity type
     * @param userId identifier of the user performing the upload
     * @return stored media metadata
     */
    List<MediaDto> saveMultipleMedia(List<MultipartFile> files, long entityId, EntityType entityType, long userId);

    /**
     * Loads media content for the specified entity as a resource.
     *
     * @param entityId target entity identifier
     * @param entityType target entity type
     * @return media resource
     */
    Resource loadMediaAsResource(long entityId, EntityType entityType);

    /**
     * Returns all media metadata linked to the specified entity.
     *
     * @param entityId target entity identifier
     * @param entityType target entity type
     * @return entity media list
     */
    List<MediaDto> findAllByEntity(long entityId, EntityType entityType);
}
