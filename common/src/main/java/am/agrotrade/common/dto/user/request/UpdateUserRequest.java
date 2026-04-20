package am.agrotrade.common.dto.user.request;

import am.agrotrade.common.dto.NotificationSettingsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserRequest(

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String name,

        @NotBlank(message = "Surname is required")
        @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
        String surname,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @Size(max = 255, message = "Address cannot exceed 255 characters")
        String address,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
        String phoneNumber,

        @Valid
        NotificationSettingsDTO notificationSettingsDTO

) {
}
