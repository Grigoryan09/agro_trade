package am.agrotrade.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request payload for verification-style notifications that require a single recipient and code")
public record VerifyNotificationRequest(

        @NotNull(message = "User ID is required")
        @Positive(message = "User ID must be positive")
        @Schema(description = "Recipient user ID", example = "12345", required = true)
        Long userId,

        @NotBlank(message = "Code is required")
        @Schema(description = "Verification or reset code", example = "123456", required = true)
        String code
) {
}
