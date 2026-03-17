package am.agrotrade.dto.user;

import am.agrotrade.dto.passport.PassportInfoDto;

public record ClientInfoDto(

        String fullName,
        String email,
        String phoneNumber,
        PassportInfoDto passportInfo

) {



}
