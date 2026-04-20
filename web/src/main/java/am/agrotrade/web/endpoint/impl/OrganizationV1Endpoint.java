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
    public OrganizationDetailsResponse getAll(long userid) {
        return new OrganizationDetailsResponse(orgService.getAllByUserId(userid));
    }

    @Override
    public OrganizationDetailsResponse getById(UserPrincipal user, long id) {
        return new OrganizationDetailsResponse(List.of(orgService.getById(user.getId(), id)));
    }

    @Override
    public OrganizationDetailsResponse create(long userId, CreateOrganizationRequest request) {
        return new OrganizationDetailsResponse(List.of(orgService.create(userId, request)));
    }

    @Override
    public OrganizationDetailsResponse update(long userId, long id, UpdateOrganizationRequest request) {
        return new OrganizationDetailsResponse(List.of(orgService.update(userId, id, request)));
    }

    @Override
    public void delete(long userId, long id) {
        orgService.delete(userId, id);
    }
}
