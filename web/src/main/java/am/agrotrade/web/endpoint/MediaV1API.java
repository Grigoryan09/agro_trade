package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
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
     * Uploads or updates the avatar for the currently authenticated user.
     * * @param userId the ID of the authenticated user, resolved from the security context.
     * @param file   the multipart file representing the image to be used as an avatar.
     * @return a {@link MediaResponse} containing the metadata and storage path of the uploaded avatar.
     */
    @PostMapping("/avatar")
    MediaResponse uploadAvatar(
            @CurrentUserId long userId,
            @RequestParam("file") MultipartFile file
    );

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
    List<MediaResponse> uploadImages(
            @PathVariable EntityType entityType,
            @PathVariable long entityId,
            @RequestParam("files") List<MultipartFile> files);
}