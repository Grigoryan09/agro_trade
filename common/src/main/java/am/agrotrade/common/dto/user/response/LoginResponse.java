package am.agrotrade.common.dto.user.response;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}