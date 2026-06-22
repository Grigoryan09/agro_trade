package am.agrotrade.common.event;

public record OrderCreatedEvent(
        String email,
        String title,
        String message,
        String notificationType
) {
}
