package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.common.dto.user.response.BaseUserInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping()
    BaseUserInfoResponse get(
            @AuthenticationPrincipal UserPrincipal user);

    /**
     * Updates the personal profile information for the authenticated user.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link UpdateUserRequest} containing updated personal details
     * @return {@link BaseUserInfoResponse} representing the updated user state
     */
    @PutMapping()
    BaseUserInfoResponse update(
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
}
