package am.agrotrade.common.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyRequest(

        @NotBlank
        @Email(message = "Invalid email")
        String email,

        @NotBlank @Size(min = 6, max = 6)
        String code
) {
}
