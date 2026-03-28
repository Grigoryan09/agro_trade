package am.agrotrade.common.dto.user.response;

public record LoginDto(
        String accessToken,
        String refreshToken,
        String tokenType
) {}