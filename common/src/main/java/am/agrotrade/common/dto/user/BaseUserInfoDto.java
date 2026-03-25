package am.agrotrade.common.dto.user;

import am.agrotrade.common.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
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
