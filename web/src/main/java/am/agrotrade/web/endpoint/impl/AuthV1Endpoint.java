package am.agrotrade.web.endpoint.impl;

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
import am.agrotrade.core.service.AuthService;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.web.endpoint.AuthV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class AuthV1Endpoint implements AuthV1API {

    private final AuthService authService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        return new RegisterResponse(authService.register(request));
    }

    @Override
    public VerifyResponse verify(VerifyRequest request) {
        return new VerifyResponse(authService.verify(request.email(), request.code()));
    }

    @Override
    public ResendCodeResponse resendVerificationCode(ResendCodeRequest request) {
        return new ResendCodeResponse(authService.resendVerificationCode(request.email()));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return new LoginResponse(authService.login(request));
    }

    @Override
    public void logout(UserPrincipal principal) {
        authService.logout(principal.user().getId());
    }
}
