package am.agrotrade.dto.user.response;

import am.agrotrade.dto.user.UserForAdminDto;

import java.util.List;

public record UserForAdminResponse(

        List<UserForAdminDto> users

){
}
