package am.agrotrade.web.endpoint.impl;

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
import am.agrotrade.core.service.AuthService;
import am.agrotrade.core.service.security.UserPrincipal;
import am.agrotrade.web.endpoint.AuthV1API;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthV1Endpoint implements AuthV1API {

    private final AuthService authService;

    @Override
    public RegisterDto register(RegisterRequest request) {
        return authService.register(request);
    }

    @Override
    public VerifyDto verify(VerifyRequest request) {
        return authService.verify(request.email(), request.code());
    }

    @Override
    public ResendCodeDto resendVerificationCode(ResendCodeRequest request) {
        return authService.resendVerificationCode(request.email());
    }

    @Override
    public LoginDto login(LoginRequest request) {
        return authService.login(request);
    }

    @Override
    public RefreshTokenDto refresh(RefreshRequest request) {
        return authService.refresh(request.refreshToken());
    }

    @Override
    public void logout(UserPrincipal principal) {
        authService.logout(principal.user().getId());
    }
}
