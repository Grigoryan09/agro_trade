package am.agrotrade.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Request payload for order-opened notifications sent to multiple recipients")
public record OrderNotificationRequest(

        @NotEmpty(message = "User IDs list must not be empty")
        @Schema(description = "Recipient user IDs", example = "[1, 2, 3]", required = true)
        List<Long> userIds,

        @NotNull(message = "Order details are required")
        @Valid
        @Schema(description = "Order data used by the notification template", required = true)
        OrderOpenedDto orderOpenedDto
) {
}
