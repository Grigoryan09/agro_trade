package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.core.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for handling media-related operations within the Agro Trade service.
 * <p>
 * This includes uploading user avatars or other media files associated with a user.
 * The controller separates media management from user profile endpoints.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/media")
public interface MediaV1API {

    /**
     * Uploads a media file (e.g., user avatar) for the authenticated user.
     * <p>
     * Expected formats usually include JPG, PNG, or other supported image types.
     *
     * @param userPrincipal the authenticated user's security principal
     * @param file          the media file to upload
     * @return a {@link MediaResponse} containing metadata about the uploaded file
     */
    @PostMapping("/avatar")
    MediaResponse uploadAvatar(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam("file") MultipartFile file
    );
}
