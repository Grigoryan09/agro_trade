package am.agrotrade.common.dto.order.request;

import am.agrotrade.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(

        @NotNull(message = "Order status cannot be null")
        OrderStatus orderStatus

) {
}