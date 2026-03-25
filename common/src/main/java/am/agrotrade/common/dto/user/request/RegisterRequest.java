package am.agrotrade.common.dto.user.request;


import am.agrotrade.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Surname is required")
        String surname,

        Gender gender,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate,

        @NotBlank(message = "Address is required")
        String address,

        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
){
}



