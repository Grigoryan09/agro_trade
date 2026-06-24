package am.agrotrade.common.event;

public record VerificationCodeResentEvent(
        long userId,
        String verificationCode,
        String email
) {}