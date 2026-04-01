package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.RefreshTokenRequest;
import am.agrotrade.common.dto.user.response.RefreshTokenResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/token")
public interface TokenV1API {

    /**
     * Issues a new access token using a valid, non-expired refresh token.
     *
     * @param request the {@link RefreshTokenRequest} containing the current refresh token
     * @return {@link RefreshTokenResponse} containing the new access and refresh tokens
     */
    @PostMapping("/refresh")
    RefreshTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request);
}
