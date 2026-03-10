package am.agrotrade;

import am.agrotrade.dto.user.request.AuthUserRequest;
import am.agrotrade.dto.user.request.LoginUserRequest;
import am.agrotrade.dto.user.request.UpdateUserPasswordRequest;
import am.agrotrade.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(AuthUserRequest autoUserRequest);

    User toEntity(LoginUserRequest loginUserRequest);

    User toEntity(UpdateUserPasswordRequest updateUserRequest);



}
