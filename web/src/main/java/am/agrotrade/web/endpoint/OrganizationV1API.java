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
 * REST API for managing user organizations in the Agro Trade system.
 * This interface defines the version 1 endpoints for CRUD operations on organizations.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/user/organizations")
public interface OrganizationV1API {

    /**
     * Retrieves all organizations associated with the currently authenticated user.
     *
     * @param userPrincipal the authenticated user's details
     * @return a list of organizations belonging to the user
     */
    @GetMapping
    List<OrganizationDetailsResponse> getAll(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    );

    /**
     * Retrieves specific organization details by its unique identifier.
     *
     * @param userPrincipal the authenticated user's details
     * @param id   the unique identifier of the organization
     * @return the details of the requested organization
     */
    @GetMapping("/{id}")
    OrganizationDetailsResponse getById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long id
    );

    /**
     * Creates a new organization for the authenticated user.
     *
     * @param userPrincipal the authenticated user's details
     * @param request       the data required to create a new organization
     * @return the details of the newly created organization
     */
    @PostMapping
    OrganizationDetailsResponse create(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CreateOrganizationRequest request
    );

    /**
     * Updates an existing organization's information.
     *
     * @param userPrincipal the authenticated user's details
     * @param id            the unique identifier of the organization to update
     * @param request       the updated organization data
     * @return the details of the updated organization
     */
    @PutMapping("/{id}")
    OrganizationDetailsResponse update(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long id,
            @Valid @RequestBody UpdateOrganizationRequest request
    );

    /**
     * Deletes an organization from the system.
     *
     * @param userPrincipal the authenticated user's details
     * @param id            the unique identifier of the organization to delete
     */
    @DeleteMapping("/{id}")
    void delete(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long id
    );
}