package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.common.dto.organization.response.OrganizationDetailsResponse;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.core.service.OrganizationService;
import am.agrotrade.web.endpoint.OrganizationV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OrganizationV1Endpoint implements OrganizationV1API {

    private final OrganizationService orgService;

    @Override
    public List<OrganizationDetailsResponse> get(UserPrincipal userPrincipal) {
       return orgService.getOrganizations(userPrincipal.getId())
                .stream()
                .map(OrganizationDetailsResponse::new)
                .toList();
    }

    @Override
    public OrganizationDetailsResponse create(UserPrincipal user, CreateOrganizationRequest request) {
        return new OrganizationDetailsResponse(orgService.add(user.getId(), request));
    }

    @Override
    public OrganizationDetailsResponse updateOrganization(UserPrincipal user, UpdateOrganizationRequest request) {
        return new OrganizationDetailsResponse(orgService.update(user.getId(), request));
    }

    @Override
    public void delete(UserPrincipal user, long id) {
        orgService.delete(id, user.getId());
    }
}
