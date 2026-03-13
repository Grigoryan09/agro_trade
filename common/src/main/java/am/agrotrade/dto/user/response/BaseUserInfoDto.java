package am.agrotrade.dto.user.response;

import am.agrotrade.model.enums.Gender;

import java.time.LocalDate;

public class BaseUserInfoDto {
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    private String email;
    private String phoneNumber;
    private String username;
}
