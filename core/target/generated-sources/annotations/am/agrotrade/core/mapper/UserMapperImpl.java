package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.SellerInfoDto;
import am.agrotrade.common.dto.user.UserForAdminDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.model.Organization;
import am.agrotrade.core.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T01:20:32+0400",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.11.0.11 (Alibaba)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterRequest autoUserRequest) {
        if ( autoUserRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( autoUserRequest.name() );
        user.surname( autoUserRequest.surname() );
        user.gender( autoUserRequest.gender() );
        user.birthDate( autoUserRequest.birthDate() );
        user.address( autoUserRequest.address() );
        user.email( autoUserRequest.email() );
        user.phoneNumber( autoUserRequest.phoneNumber() );
        user.username( autoUserRequest.username() );
        user.password( autoUserRequest.password() );

        return user.build();
    }

    @Override
    public User toEntity(LoginRequest loginRequest) {
        if ( loginRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( loginRequest.username() );
        user.password( loginRequest.password() );

        return user.build();
    }

    @Override
    public User toEntity(ChangePasswordRequest updateUserRequest) {
        if ( updateUserRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        return user.build();
    }

    @Override
    public BaseUserInfoDto toBaseUserInfoDto(User user) {
        if ( user == null ) {
            return null;
        }

        BaseUserInfoDto baseUserInfoDto = new BaseUserInfoDto();

        baseUserInfoDto.setName( user.getName() );
        baseUserInfoDto.setSurname( user.getSurname() );
        baseUserInfoDto.setGender( user.getGender() );
        baseUserInfoDto.setBirthDate( user.getBirthDate() );
        baseUserInfoDto.setAddress( user.getAddress() );
        baseUserInfoDto.setEmail( user.getEmail() );
        baseUserInfoDto.setPhoneNumber( user.getPhoneNumber() );
        baseUserInfoDto.setUsername( user.getUsername() );

        return baseUserInfoDto;
    }

    @Override
    public UserForAdminDto toUserForAdminDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserForAdminDto userForAdminDto = new UserForAdminDto();

        userForAdminDto.setName( user.getName() );
        userForAdminDto.setSurname( user.getSurname() );
        userForAdminDto.setGender( user.getGender() );
        userForAdminDto.setBirthDate( user.getBirthDate() );
        userForAdminDto.setAddress( user.getAddress() );
        userForAdminDto.setEmail( user.getEmail() );
        userForAdminDto.setPhoneNumber( user.getPhoneNumber() );
        userForAdminDto.setUsername( user.getUsername() );
        userForAdminDto.setId( user.getId() );
        List<Role> list = user.getRoles();
        if ( list != null ) {
            userForAdminDto.setRoles( new ArrayList<Role>( list ) );
        }
        userForAdminDto.setActive( user.isActive() );

        return userForAdminDto;
    }

    @Override
    public SellerInfoDto toSellerInfoDto(User user) {
        if ( user == null ) {
            return null;
        }

        String sellerId = null;
        String sellerName = null;
        OrganizationDetailsDto organization = null;

        sellerId = String.valueOf( user.getId() );
        sellerName = user.getName();
        organization = organizationToOrganizationDetailsDto( user.getOrganization() );

        SellerInfoDto sellerInfoDto = new SellerInfoDto( sellerId, sellerName, organization );

        return sellerInfoDto;
    }

    @Override
    public void updateUserFromRequest(UpdateUserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            user.setName( request.name() );
        }
        if ( request.surname() != null ) {
            user.setSurname( request.surname() );
        }
        if ( request.birthDate() != null ) {
            user.setBirthDate( request.birthDate() );
        }
        if ( request.address() != null ) {
            user.setAddress( request.address() );
        }
        if ( request.phoneNumber() != null ) {
            user.setPhoneNumber( request.phoneNumber() );
        }
    }

    protected OrganizationDetailsDto organizationToOrganizationDetailsDto(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        String name = null;
        String licenseNumber = null;
        String address = null;
        String contactNumber = null;
        String email = null;

        name = organization.getName();
        licenseNumber = organization.getLicenseNumber();
        address = organization.getAddress();
        contactNumber = organization.getContactNumber();
        email = organization.getEmail();

        BaseUserInfoDto userInfoDto = null;

        OrganizationDetailsDto organizationDetailsDto = new OrganizationDetailsDto( name, licenseNumber, address, contactNumber, email, userInfoDto );

        return organizationDetailsDto;
    }
}
