package am.agrotrade.core.mapper;

import am.agrotrade.core.model.Organization;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {

    Organization toEntity(CreateOrganizationRequest organizationRequest);

}
