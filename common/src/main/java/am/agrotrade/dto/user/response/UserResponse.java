package am.agrotrade.dto.user.response;

import am.agrotrade.model.enums.Gender;

import java.time.LocalDate;

public record UserResponse(

        String name,
        String surname,
        Gender gender,
        LocalDate birthDate,
        String address,
        String email,
        String phoneNumber,
        String username
) {
}
