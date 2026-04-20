package am.agrotrade.core.service;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;

import java.util.List;

/**
 * Manages organization records belonging to users.
 */
public interface OrganizationService {

    /**
     * Returns all organizations owned by the specified user.
     *
     * @param userId user identifier
     * @return organization list
     */
    List<OrganizationDetailsDto> getAllByUserId(long userId);

    /**
     * Returns a user's organization by identifier.
     *
     * @param userId user identifier
     * @param organizationId organization identifier
     * @return organization data
     */
    OrganizationDetailsDto getById(long userId, long organizationId);

    /**
     * Creates an organization for the specified user.
     *
     * @param userId user identifier
     * @param request organization payload
     * @return created organization data
     */
    OrganizationDetailsDto create(long userId, CreateOrganizationRequest request);

    /**
     * Updates an organization owned by the specified user.
     *
     * @param userId user identifier
     * @param organizationId organization identifier
     * @param request updated organization payload
     * @return updated organization data
     */
    OrganizationDetailsDto update(long userId, long organizationId, UpdateOrganizationRequest request);

    /**
     * Deletes an organization owned by the specified user.
     *
     * @param userId user identifier
     * @param organizationId organization identifier
     */
    void delete(long userId, long organizationId);
}
