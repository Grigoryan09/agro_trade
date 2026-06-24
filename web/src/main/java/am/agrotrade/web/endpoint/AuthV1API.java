package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.ResendCodeRequest;
import am.agrotrade.common.dto.user.request.VerifyRequest;
import am.agrotrade.common.dto.user.response.LoginResponse;
import am.agrotrade.common.dto.user.response.RegisterResponse;
import am.agrotrade.common.dto.user.response.ResendCodeResponse;
import am.agrotrade.common.dto.user.response.VerifyResponse;
import am.agrotrade.core.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth V1", description = "Authentication and account verification endpoints.")
@RestController
@RequestMapping("/agro-trade-service/api/v1/auth")
public interface AuthV1API {

    @Operation(summary = "Register user", description = "Creates a new user account and sends a verification code.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration request", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/register")
    RegisterResponse register(@Valid @RequestBody RegisterRequest request);

    @Operation(summary = "Verify user", description = "Verifies a user account with a previously issued code.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired verification code", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/verify")
    VerifyResponse verify(@Valid @RequestBody VerifyRequest request);

    @Operation(summary = "Resend verification code", description = "Sends a new verification code to the user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verification code resent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid resend request", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/resend-code")
    ResendCodeResponse resendVerificationCode(@Valid @RequestBody ResendCodeRequest request);

    @Operation(summary = "Login", description = "Authenticates a user and returns access and refresh tokens.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "400", description = "Invalid login request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest request);

    @Operation(summary = "Logout", description = "Invalidates the authenticated user's current session.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })

    @PostMapping("/logout")
    void logout(@Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal principal);
}
