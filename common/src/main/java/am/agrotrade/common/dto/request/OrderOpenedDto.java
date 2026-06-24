package am.agrotrade.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Order data required for order-opened notifications")
public record OrderOpenedDto(
        @NotBlank(message = "Order URL is required")
        @Schema(description = "Frontend URL for the created order", example = "https://app.example.com/orders/123", required = true)
        String orderUrl,

        @NotBlank(message = "Product name is required")
        @Schema(description = "Product name shown in the email", example = "Wheat", required = true)
        String productName) {
}
