package am.agrotrade.common.dto.passport.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateAndUpdatePassportRequest(

        @NotBlank(message = "Passport number is required")
        @Pattern(regexp = "^[A-Z0-9]{6,15}$", message = "Passport number must be 6-15 alphanumeric characters")
        String passportNumber,

        @NotNull(message = "Issue date is required")
        @PastOrPresent(message = "Issue date cannot be in the future")
        LocalDate issueDate,

        @NotNull(message = "Expiry date is required")
        @Future(message = "Expiry date must be in the future")
        LocalDate expiryDate,

        @NotBlank(message = "Issuing authority (Issued By) is required")
        @Size(max = 255, message = "Issued By name is too long")
        String issuedBy
) {
}
