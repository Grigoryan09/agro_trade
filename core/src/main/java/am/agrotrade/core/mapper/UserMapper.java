package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.SellerInfoDto;
import am.agrotrade.common.dto.user.UserForAdminDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.core.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(RegisterRequest autoUserRequest);

    User toEntity(LoginRequest loginRequest);

    User toEntity(ChangePasswordRequest updateUserRequest);

    BaseUserInfoDto toBaseUserInfoDto(User user);

    UserForAdminDto toUserForAdminDto(User user);

    @Mapping(source = "id", target = "sellerId")
    @Mapping(source = "name", target = "sellerName")
    SellerInfoDto toSellerInfoDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);


}
