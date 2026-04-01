package am.agrotrade.core.service;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;

import java.util.List;

public interface OrganizationService {

    List<OrganizationDetailsDto> getAllByUserId(long userId);

    OrganizationDetailsDto getById(long userId, long organizationId);

    OrganizationDetailsDto create(long userId, CreateOrganizationRequest request);

    OrganizationDetailsDto update(long userId, long organizationId, UpdateOrganizationRequest request);

    void delete(long userId, long organizationId);
}
