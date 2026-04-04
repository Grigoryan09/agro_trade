package am.agrotrade.common.dto.user;

public record LoginDto(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}