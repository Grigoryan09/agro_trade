package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RefreshRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.ResendCodeRequest;
import am.agrotrade.common.dto.user.request.ResendCodeResponse;
import am.agrotrade.common.dto.user.request.VerifyRequest;
import am.agrotrade.common.dto.user.response.LoginResponse;
import am.agrotrade.common.dto.user.response.RefreshTokenResponse;
import am.agrotrade.common.dto.user.response.RegisterResponse;
import am.agrotrade.common.dto.user.response.VerifyResponse;
import am.agrotrade.core.service.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/agro-trade-service/api/v1/auth")
public interface AuthV1API {

    @PostMapping("/register")
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request);

    @PostMapping("/verify")
    ResponseEntity<VerifyResponse> verify(@Valid @RequestBody VerifyRequest request);

    @PostMapping("/resend-code")
    ResponseEntity<ResendCodeResponse> resendVerificationCode(@RequestBody ResendCodeRequest request);

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/refresh")
    ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshRequest request);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@Valid @AuthenticationPrincipal UserPrincipal principal);
}
