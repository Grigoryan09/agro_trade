package am.agrotrade.common.dto.user.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}