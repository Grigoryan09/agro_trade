package am.agrotrade.common.dto.user.request;

public record ResendCodeDto(

        String message,
        String username
) {
}