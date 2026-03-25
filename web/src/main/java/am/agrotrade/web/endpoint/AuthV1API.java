package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RefreshRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.response.AuthResponse;
import am.agrotrade.core.service.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/agro-trade/api/v1/auth")
public interface AuthV1API {

    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request);

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/refresh")
    ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@Valid @AuthenticationPrincipal UserPrincipal principal);
}
