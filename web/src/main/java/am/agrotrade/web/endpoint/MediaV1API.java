package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.common.enums.EntityType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST API for media management in the AgroTrade system.
 * <p>
 * Provides endpoints for uploading user avatars and handling bulk image
 * uploads for various system entities like Products and News.
 * </p>
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/media")
public interface MediaV1API {

    /**
     * Uploads multiple images and associates them with a specific system entity.
     * <p>
     * This is a generic endpoint used for entities that support multiple media files,
     * such as {@link EntityType#PRODUCT} or {@link EntityType#NEWS}.
     * </p>
     *
     * @param entityType the type of the entity (e.g., PRODUCT, NEWS).
     * @param entityId   the unique identifier of the target entity.
     * @param files      a list of multipart files to be stored and linked to the entity.
     * @return a list of {@link MediaResponse} objects representing all successfully saved files.
     */
    @PostMapping("/{entityType}/{entityId}")
    MediaResponse uploadImages(
            @PathVariable EntityType entityType,
            @PathVariable long entityId,
            @RequestParam("files") List<MultipartFile> files);
}