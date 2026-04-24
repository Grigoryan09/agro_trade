package am.agrotrade.common.event;

public record NotificationOrderCreatedEvent(
        long sellerId,
        long managerId,
        String productName,
        String orderUrl
) {}