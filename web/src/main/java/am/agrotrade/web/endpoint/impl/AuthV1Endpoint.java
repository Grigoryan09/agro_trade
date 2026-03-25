package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RefreshRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.response.AuthResponse;
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
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    public ResponseEntity<AuthResponse> refresh(RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request.refreshToken()));
    }

    @Override
    public ResponseEntity<Void> logout(UserPrincipal principal) {
        authService.logout(principal.user().getId());
        return ResponseEntity.ok().build();
    }
}
