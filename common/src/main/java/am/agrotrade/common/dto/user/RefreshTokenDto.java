package am.agrotrade.common.dto.user;

public record RefreshTokenDto(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}