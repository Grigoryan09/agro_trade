package am.agrotrade.dto.order.request;

import java.math.BigDecimal;

public record CreateOrderRequest(

        long buyerId,
        long sellerId,
        long productId,
        long quantity,
        BigDecimal totalPrice

) {
}
