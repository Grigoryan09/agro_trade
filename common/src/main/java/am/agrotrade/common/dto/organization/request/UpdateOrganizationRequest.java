package am.agrotrade.common.dto.organization.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateOrganizationRequest(

        @NotNull
        long organizationId,

        @NotBlank(message = "Organization name is required")
        @Size(min = 2, max = 100, message = "Organization name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "License number is required")
        @Pattern(regexp = "^[A-Z0-9-]{5,20}$", message = "License number must be 5-20 alphanumeric characters")
        String licenseNumber,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address cannot exceed 255 characters")
        String address,

        @NotBlank(message = "Contact number is required")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Contact phone must be between 10 and 15 digits")
        String contactNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid business email address")
        String email

) {
}
