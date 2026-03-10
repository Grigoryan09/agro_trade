package am.agrotrade.mapper;

import am.agrotrade.dto.user.request.AuthUserRequest;
import am.agrotrade.dto.user.request.LoginUserRequest;
import am.agrotrade.dto.user.request.UpdateUserPasswordRequest;
import am.agrotrade.dto.user.request.UpdateUserRequest;
import am.agrotrade.dto.user.response.AuthResponse;
import am.agrotrade.dto.user.response.UserResponse;
import am.agrotrade.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toEntity(AuthUserRequest autoUserRequest);

    User toEntity(LoginUserRequest loginUserRequest);

    User toEntity(UpdateUserPasswordRequest updateUserRequest);

    UserResponse toResponse(User user);

    @Mapping(target = "user", source = "user")
    AuthResponse toAuthResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);



}
