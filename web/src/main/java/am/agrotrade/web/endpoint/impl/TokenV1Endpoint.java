package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.user.request.RefreshTokenRequest;
import am.agrotrade.common.dto.user.response.RefreshTokenResponse;
import am.agrotrade.core.service.AuthService;
import am.agrotrade.web.endpoint.TokenV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenV1Endpoint implements TokenV1API {

    private final AuthService authService;

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        return new RefreshTokenResponse(authService.refresh(request.refreshToken()));
    }
}
