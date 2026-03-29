package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RefreshTokenRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.ResendCodeRequest;
import am.agrotrade.common.dto.user.request.VerifyRequest;
import am.agrotrade.common.dto.user.response.LoginResponse;
import am.agrotrade.common.dto.user.response.RefreshTokenResponse;
import am.agrotrade.common.dto.user.response.RegisterResponse;
import am.agrotrade.common.dto.user.response.ResendCodeResponse;
import am.agrotrade.common.dto.user.response.VerifyResponse;
import am.agrotrade.core.service.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agro-trade-service/api/v1/auth")
public interface AuthV1API {

    /**
     * Registers a new user in the system.
     *
     * @param request the registration request containing user details and credentials
     * @return RegisterDto with created user information
     */
    @PostMapping("/register")
    RegisterResponse register(@Valid @RequestBody RegisterRequest request);

    /**
     * Verifies a user using a verification code.
     *
     * @param request the verification request containing code and user identifier
     * @return VerifyDto with created user information
     */
    @PostMapping("/verify")
    VerifyResponse verify(@Valid @RequestBody VerifyRequest request);

    /**
     * Resends the verification code to the user.
     *
     * @param request the request containing user identification data
     * @return ResendCodeDto with resend status information
     */
    @PostMapping("/resend-code")
    ResendCodeResponse resendVerificationCode(@Valid @RequestBody ResendCodeRequest request);

    /**
     * Authenticates a user and returns access and refresh tokens.
     *
     * @param request the login request containing credentials
     * @return LoginDto with authentication tokens
     */
    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest request);

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param request the refresh request containing refresh token
     * @return RefreshTokenDto with new access token
     */
    @PostMapping("/refresh")
    RefreshTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request);

    /**
     * Logs out the authenticated user by invalidating tokens.
     *
     * @param principal the currently authenticated user
     */
    @PostMapping("/logout")
    void logout(@AuthenticationPrincipal UserPrincipal principal);
}
