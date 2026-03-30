package am.agrotrade.core.service;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;

import java.util.List;

public interface OrganizationService {

    List<OrganizationDetailsDto> getOrganizations(long userId);

    OrganizationDetailsDto add(long userId, CreateOrganizationRequest request);

    OrganizationDetailsDto update(long userId, UpdateOrganizationRequest request);

    void delete(long orgId, long userId);
}
