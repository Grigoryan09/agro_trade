package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.core.model.Organization;
import am.agrotrade.core.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T01:20:33+0400",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.11.0.11 (Alibaba)"
)
@Component
public class OrganizationMapperImpl implements OrganizationMapper {

    @Override
    public Organization toEntity(CreateOrganizationRequest organizationRequest) {
        if ( organizationRequest == null ) {
            return null;
        }

        Organization organization = new Organization();

        organization.setName( organizationRequest.name() );
        organization.setLicenseNumber( organizationRequest.licenseNumber() );
        organization.setAddress( organizationRequest.address() );
        organization.setContactNumber( organizationRequest.contactNumber() );
        organization.setEmail( organizationRequest.email() );

        return organization;
    }

    @Override
    public Organization toEntity(UpdateOrganizationRequest organizationRequest) {
        if ( organizationRequest == null ) {
            return null;
        }

        Organization organization = new Organization();

        organization.setName( organizationRequest.name() );
        organization.setLicenseNumber( organizationRequest.licenseNumber() );
        organization.setAddress( organizationRequest.address() );
        organization.setContactNumber( organizationRequest.contactNumber() );
        organization.setEmail( organizationRequest.email() );

        return organization;
    }

    @Override
    public OrganizationDetailsDto toDto(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        BaseUserInfoDto userInfoDto = null;
        String name = null;
        String licenseNumber = null;
        String address = null;
        String contactNumber = null;
        String email = null;

        userInfoDto = userToBaseUserInfoDto( organization.getUser() );
        name = organization.getName();
        licenseNumber = organization.getLicenseNumber();
        address = organization.getAddress();
        contactNumber = organization.getContactNumber();
        email = organization.getEmail();

        OrganizationDetailsDto organizationDetailsDto = new OrganizationDetailsDto( name, licenseNumber, address, contactNumber, email, userInfoDto );

        return organizationDetailsDto;
    }

    @Override
    public List<OrganizationDetailsDto> toDtoList(List<Organization> organizations) {
        if ( organizations == null ) {
            return null;
        }

        List<OrganizationDetailsDto> list = new ArrayList<OrganizationDetailsDto>( organizations.size() );
        for ( Organization organization : organizations ) {
            list.add( toDto( organization ) );
        }

        return list;
    }

    @Override
    public void updateOrganizationFromRequest(UpdateOrganizationRequest request, Organization organization) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            organization.setName( request.name() );
        }
        if ( request.licenseNumber() != null ) {
            organization.setLicenseNumber( request.licenseNumber() );
        }
        if ( request.address() != null ) {
            organization.setAddress( request.address() );
        }
        if ( request.contactNumber() != null ) {
            organization.setContactNumber( request.contactNumber() );
        }
        if ( request.email() != null ) {
            organization.setEmail( request.email() );
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
