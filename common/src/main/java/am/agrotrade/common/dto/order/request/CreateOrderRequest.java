package am.agrotrade.common.dto.order.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderRequest(

        @NotNull(message = "Seller ID is required")
        @Positive(message = "Seller ID must be a positive number")
        long sellerId,

        @NotNull(message = "Product ID is required")
        @Positive(message = "Product ID must be a positive number")
        long productId,

        @Positive(message = "Quantity must be greater than zero")
        long quantity,

        @NotNull(message = "Total price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Price format is invalid")
        BigDecimal price

) {
}
