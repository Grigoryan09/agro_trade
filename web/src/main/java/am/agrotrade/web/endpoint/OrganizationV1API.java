package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.common.dto.organization.response.OrganizationDetailsResponse;
import am.agrotrade.core.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing organization profiles within the Agro Trade service.
 * <p>
 * This interface provides endpoints for users to manage their business or
 * legal entity information, including registration, updates, and retrieval
 * of organization-specific data.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/user/organization")
public interface OrganizationV1API {

    /**
     * Retrieves the details of the organization associated with the authenticated user.
     *
     * @param userPrincipal the authenticated user's security principal
     * @return {@link OrganizationDetailsResponse} containing the organization's profile information
     */
    @GetMapping("/get")
    List<OrganizationDetailsResponse> get(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    );

    /**
     * Registers a new organization for the authenticated user.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link UpdateOrganizationRequest} containing the
     *                organization's legal and contact information
     * @return {@link OrganizationDetailsResponse} representing the newly created organization
     */
    @PostMapping("/create")
    OrganizationDetailsResponse create(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody CreateOrganizationRequest request
    );

    /**
     * Updates the existing organization information for the authenticated user.
     *
     * @param user    the authenticated user's security principal
     * @param request the {@link UpdateOrganizationRequest} with updated details
     * @return {@link OrganizationDetailsResponse} representing the updated organization state
     */
    @PutMapping("/update-organization")
    OrganizationDetailsResponse updateOrganization(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody UpdateOrganizationRequest request
    );

    /**
     * Deletes the organization record associated with the authenticated user.
     * <p>
     * Use with caution as this may impact related microservices like leasing or banking contracts.
     *
     * @param user the authenticated user's security principal
     */
    @DeleteMapping("/{id}")
    void delete(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable long id);
}