package am.agrotrade.common.event;

public record UserRegisteredEvent(
        Long userId,
        String email,
        String verificationCode,
        Boolean emailEnabled,
        Boolean smsEnabled,
        Boolean inAppEnabled
) {}