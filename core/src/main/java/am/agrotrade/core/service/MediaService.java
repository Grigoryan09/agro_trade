package am.agrotrade.core.service;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.enums.EntityType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for handling media operations within the AgroTrade system.
 * Provides methods for uploading, retrieving, and managing physical files
 * and their associated metadata for various entities like Users and Products.
 */
public interface MediaService {
    /**
     * Persists multiple media files for a single entity in a batch operation.
     *
     * @param files      the list of multipart files to upload.
     * @param entityId   the ID of the target entity.
     * @param entityType the category of the target entity.
     * @return a list of {@link MediaDto} objects for all successfully saved files.
     */
    List<MediaDto> saveMultipleMedia(List<MultipartFile> files, long entityId, EntityType entityType);

    /**
     * Retrieves a media file as a byte-stream resource for download or display.
     * Usually fetches the most recent file if multiple exist for the given entity.
     *
     * @param entityId   the ID of the entity.
     * @param entityType the category of the entity.
     * @return the file content wrapped as a {@link Resource}.
     * @throws am.agrotrade.core.exception.ResourceNotFoundException if no media is found.
     */
    Resource loadMediaAsResource(long entityId, EntityType entityType);

    /**
     * Retrieves all media metadata records associated with a specific entity.
     *
     * @param entityId   the ID of the entity.
     * @param entityType the category of the entity.
     * @return a list of {@link MediaDto} objects linked to the entity.
     */
    List<MediaDto> findAllByEntity(long entityId, EntityType entityType);

}