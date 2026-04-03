package am.agrotrade.common.dto.user;

import am.agrotrade.common.dto.passport.PassportInfoDto;

public record ClientInfoDto(

        String fullName,
        String email,
        String phoneNumber,
        PassportInfoDto passportInfo

) {


}
