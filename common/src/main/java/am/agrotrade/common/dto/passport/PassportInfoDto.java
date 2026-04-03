package am.agrotrade.common.dto.passport;

import am.agrotrade.common.dto.user.BaseUserInfoDto;

public record PassportInfoDto(

        String passportNumber,
        BaseUserInfoDto userInfoDto

) {
}
