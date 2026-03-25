package am.agrotrade.common.dto.user.request;

import am.agrotrade.common.enums.Gender;

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
