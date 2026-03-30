package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.common.dto.user.response.BaseUserInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing user profile information and security settings.
 * <p>
 * This API allows authenticated users to retrieve their profile details,
 * update personal information, and securely change their account password.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/user")
public interface UserV1API {

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @param user the authenticated user's security principal
     * @return {@link BaseUserInfoResponse} containing core user profile details
     */
    @GetMapping("/profile")
    BaseUserInfoResponse getProfile(
            @AuthenticationPrincipal UserPrincipal user);

    /**
     * Updates the personal profile information for the authenticated user.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link UpdateUserRequest} containing updated personal details
     * @return {@link BaseUserInfoResponse} representing the updated user state
     */
    @PutMapping("/update-profile")
    BaseUserInfoResponse updateProfile(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody UpdateUserRequest request);

    /**
     * Updates the password for the authenticated user.
     * <p>
     * Implementation should ensure the verification of the old password
     * before applying the change.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link ChangePasswordRequest} containing old and new passwords
     * @return {@link BaseUserInfoResponse} representing the user after the password update
     */
    @PutMapping("/change-password")
    BaseUserInfoResponse changePassword(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody ChangePasswordRequest request
    );

    /**
     * Uploads and updates the profile avatar for the currently authenticated user.
     * * <p>This method processes an image file provided via a multipart request,
     * associates it with the user's account extracted from the security context,
     * and typically stores it in a file system or database.</p>
     *
     * @param user The currently authenticated {@link UserPrincipal} obtained from the SecurityContext.
     * Used to identify the owner of the avatar.
     * @param file The {@link MultipartFile} object containing the image data.
     * Expected formats usually include JPG, PNG, or other supported image types.
     */
    @PostMapping("/profile/avatar")
    MediaResponse uploadAvatar(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam("file") MultipartFile file
    );

}
