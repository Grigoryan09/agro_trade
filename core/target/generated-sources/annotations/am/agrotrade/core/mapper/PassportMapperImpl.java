package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.core.model.Passport;
import am.agrotrade.core.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T01:20:33+0400",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.11.0.11 (Alibaba)"
)
@Component
public class PassportMapperImpl implements PassportMapper {

    @Override
    public PassportInfoDto toDto(Passport passport) {
        if ( passport == null ) {
            return null;
        }

        BaseUserInfoDto userInfoDto = null;
        String passportNumber = null;

        userInfoDto = userToBaseUserInfoDto( passport.getUser() );
        passportNumber = passport.getPassportNumber();

        PassportInfoDto passportInfoDto = new PassportInfoDto( passportNumber, userInfoDto );

        return passportInfoDto;
    }

    @Override
    public Passport toEntity(CreateAndUpdatePassportRequest passportRequest) {
        if ( passportRequest == null ) {
            return null;
        }

        Passport passport = new Passport();

        passport.setPassportNumber( passportRequest.passportNumber() );
        passport.setIssueDate( passportRequest.issueDate() );
        passport.setExpiryDate( passportRequest.expiryDate() );
        passport.setIssuedBy( passportRequest.issuedBy() );

        return passport;
    }

    @Override
    public void updatePassportFromRequest(CreateAndUpdatePassportRequest request, Passport passport) {
        if ( request == null ) {
            return;
        }

        if ( request.passportNumber() != null ) {
            passport.setPassportNumber( request.passportNumber() );
        }
        if ( request.issueDate() != null ) {
            passport.setIssueDate( request.issueDate() );
        }
        if ( request.expiryDate() != null ) {
            passport.setExpiryDate( request.expiryDate() );
        }
        if ( request.issuedBy() != null ) {
            passport.setIssuedBy( request.issuedBy() );
        }
    }

    protected BaseUserInfoDto userToBaseUserInfoDto(User user) {
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
}
