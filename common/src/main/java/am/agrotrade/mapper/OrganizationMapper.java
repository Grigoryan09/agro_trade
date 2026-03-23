package am.agrotrade.mapper;

import am.agrotrade.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.model.entity.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization toEntity(CreateOrganizationRequest organizationRequest);

}
