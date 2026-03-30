package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.core.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {

    Organization toEntity(CreateOrganizationRequest organizationRequest);

    Organization toEntity(UpdateOrganizationRequest organizationRequest);

    @Mapping(target = "userInfoDto", source = "user")
    OrganizationDetailsDto toDto(Organization organization);

    List<OrganizationDetailsDto> toDtoList(List<Organization> organizations);

}
