package am.agrotrade.common.dto.user.response;

import am.agrotrade.common.dto.user.UserForAdminDto;

import java.util.List;

public record UsersForAdminResponse(List<UserForAdminDto> users) {
}
