package am.agrotrade.dto.user.request;

import am.agrotrade.model.enums.Gender;

import java.time.LocalDate;

public record UpdateUserRequest(

         String name,
         String surname,
         Gender gender,
         LocalDate birthDate,
         String address,
         String email,
         String phoneNumber
) {
}
