package am.agrotrade.common.dto.passport.response;

import am.agrotrade.common.dto.passport.PassportInfoDto;

import java.util.List;

public record PassportInfoResponse(List<PassportInfoDto> passportInfoDto) {
}
