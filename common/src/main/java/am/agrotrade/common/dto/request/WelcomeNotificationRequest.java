package am.agrotrade.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request payload for welcome notifications sent to a single recipient")
public record WelcomeNotificationRequest(

        @NotNull(message = "User ID is required")
        @Positive(message = "User ID must be positive")
        @Schema(description = "Recipient user ID", example = "12345", required = true)
        Long userId
) {
}
