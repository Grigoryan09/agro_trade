package am.agrotrade.mapper;

import am.agrotrade.dto.user.request.AuthUserRequest;
import am.agrotrade.dto.user.request.LoginUserRequest;
import am.agrotrade.dto.user.request.UpdateUserPasswordRequest;
import am.agrotrade.dto.user.response.AuthUserResponse;
import am.agrotrade.dto.user.BaseUserInfoDto;
import am.agrotrade.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toEntity(AuthUserRequest autoUserRequest);

    User toEntity(LoginUserRequest loginUserRequest);

    User toEntity(UpdateUserPasswordRequest updateUserRequest);

    BaseUserInfoDto toResponse(User user);

    @Mapping(target = "user", source = "user")
    AuthUserResponse toAuthResponse(User user);



}
