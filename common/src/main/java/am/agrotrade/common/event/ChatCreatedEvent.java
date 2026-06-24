package am.agrotrade.common.event;

public record ChatCreatedEvent(
        long orderId,
        long buyerId,
        long sellerId,
        long managerId
) {}