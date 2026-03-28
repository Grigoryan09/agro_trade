package am.agrotrade.common.dto.user.response;

public record RegisterDto(
        boolean success,
        String message
) {}