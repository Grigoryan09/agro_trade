package am.agrotrade.web.endpoint.impl;

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
import am.agrotrade.core.service.AuthService;
import am.agrotrade.core.service.security.UserPrincipal;
import am.agrotrade.web.endpoint.AuthV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthV1Endpoint implements AuthV1API {

    private final AuthService authService;

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    public ResponseEntity<VerifyResponse> verify(VerifyRequest request) {
        return ResponseEntity.ok(authService.verify(request.email(), request.code()));
    }

    @Override
    public ResponseEntity<ResendCodeResponse> resendVerificationCode(ResendCodeRequest request) {
        return ResponseEntity.ok(authService.resendVerificationCode(request.email()));
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refresh(RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request.refreshToken()));
    }

    @Override
    public ResponseEntity<Void> logout(UserPrincipal principal) {
        authService.logout(principal.user().getId());
        return ResponseEntity.ok().build();
    }
}
