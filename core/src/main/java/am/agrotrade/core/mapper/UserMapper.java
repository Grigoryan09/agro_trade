package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.UpdateUserPasswordRequest;
import am.agrotrade.core.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(RegisterRequest autoUserRequest);

    User toEntity(LoginRequest loginRequest);

    User toEntity(UpdateUserPasswordRequest updateUserRequest);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "username", target = "username")
    BaseUserInfoDto toResponse(User user);


}
