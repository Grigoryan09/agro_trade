package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.*;
import am.agrotrade.common.dto.user.response.*;
import am.agrotrade.core.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication and authorization operations.
 * <p>
 * This interface handles the security lifecycle of a user, including registration,
 * account verification, multi-factor code resending, and JWT token management
 * (login, refresh, and logout).
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/auth")
public interface AuthV1API {

    /**
     * Registers a new user in the Agro Trade system.
     * <p>
     * Initial registration creates a pending account that requires verification.
     *
     * @param request the {@link RegisterRequest} containing user credentials and details
     * @return {@link RegisterResponse} confirming the registration attempt
     */
    @PostMapping("/register")
    RegisterResponse register(@Valid @RequestBody RegisterRequest request);

    /**
     * Verifies a user's account using a unique verification code.
     * <p>
     * Typically used after registration or when a high-security action is performed.
     *
     * @param request the {@link VerifyRequest} containing the code and user identifier
     * @return {@link VerifyResponse} containing the verification status
     */
    @PostMapping("/verify")
    VerifyResponse verify(@Valid @RequestBody VerifyRequest request);

    /**
     * Generates and resends a new verification code to the user's registered contact method.
     *
     * @param request the {@link ResendCodeRequest} identifying the user
     * @return {@link ResendCodeResponse} containing the resend operation status
     */
    @PostMapping("/resend-code")
    ResendCodeResponse resendVerificationCode(@Valid @RequestBody ResendCodeRequest request);

    /**
     * Authenticates a user and issues a set of security tokens.
     *
     * @param request the {@link LoginRequest} containing username/email and password
     * @return {@link LoginResponse} containing the access token, refresh token, and user details
     */
    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest request);

    /**
     * Issues a new access token using a valid, non-expired refresh token.
     *
     * @param request the {@link RefreshTokenRequest} containing the current refresh token
     * @return {@link RefreshTokenResponse} containing the new access and refresh tokens
     */
    @PostMapping("/refresh")
    RefreshTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request);

    /**
     * Invalidates the current session and logs out the authenticated user.
     *
     * @param principal the currently authenticated {@link UserPrincipal}
     */
    @PostMapping("/logout")
    void logout(@AuthenticationPrincipal UserPrincipal principal);
}