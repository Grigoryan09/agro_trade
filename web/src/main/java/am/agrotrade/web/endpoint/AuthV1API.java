package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RefreshRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.ResendCodeRequest;
import am.agrotrade.common.dto.user.request.ResendCodeDto;
import am.agrotrade.common.dto.user.request.VerifyRequest;
import am.agrotrade.common.dto.user.response.LoginDto;
import am.agrotrade.common.dto.user.response.RefreshTokenDto;
import am.agrotrade.common.dto.user.response.RegisterDto;
import am.agrotrade.common.dto.user.response.VerifyDto;
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
    RegisterDto register(@Valid @RequestBody RegisterRequest request);

    /**
     * Verifies a user using a verification code.
     *
     * @param request the verification request containing code and user identifier
     * @return VerifyDto with created user information
     */
    @PostMapping("/verify")
    VerifyDto verify(@Valid @RequestBody VerifyRequest request);

    /**
     * Resends the verification code to the user.
     *
     * @param request the request containing user identification data
     * @return ResendCodeDto with resend status information
     */
    @PostMapping("/resend-code")
    ResendCodeDto resendVerificationCode(@Valid @RequestBody ResendCodeRequest request);

    /**
     * Authenticates a user and returns access and refresh tokens.
     *
     * @param request the login request containing credentials
     * @return LoginDto with authentication tokens
     */
    @PostMapping("/login")
    LoginDto login(@Valid @RequestBody LoginRequest request);

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param request the refresh request containing refresh token
     * @return RefreshTokenDto with new access token
     */
    @PostMapping("/refresh")
    RefreshTokenDto refresh(@Valid @RequestBody RefreshRequest request);

    /**
     * Logs out the authenticated user by invalidating tokens.
     *
     * @param principal the currently authenticated user
     */
    @PostMapping("/logout")
    void logout(@AuthenticationPrincipal UserPrincipal principal);
}
}
