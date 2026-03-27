package am.agrotrade.common.dto.user.response;

public record RegisterResponse(
        boolean success,
        String message
) {}