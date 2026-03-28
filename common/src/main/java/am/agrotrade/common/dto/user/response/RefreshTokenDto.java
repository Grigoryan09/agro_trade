package am.agrotrade.common.dto.user.response;

public record RefreshTokenDto(
        String accessToken,
        String refreshToken,
        String tokenType
) {}