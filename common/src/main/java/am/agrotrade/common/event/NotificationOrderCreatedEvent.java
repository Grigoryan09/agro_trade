package am.agrotrade.common.event;

public record NotificationOrderCreatedEvent(
        Long sellerId,
        Long managerId,
        String productName,
        String orderUrl
) {}