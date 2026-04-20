package am.agrotrade.common.dto.organization.response;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;

import java.util.List;

public record OrganizationDetailsResponse(
        List<OrganizationDetailsDto> organizationDetailsDto) {
}
