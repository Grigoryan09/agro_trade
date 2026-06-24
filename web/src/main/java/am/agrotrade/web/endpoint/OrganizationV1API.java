package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.organization.request.CreateOrganizationRequest;
import am.agrotrade.common.dto.organization.request.UpdateOrganizationRequest;
import am.agrotrade.common.dto.organization.response.OrganizationDetailsResponse;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Organization V1", description = "Organization management endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/user/organizations")
public interface OrganizationV1API {

    @Operation(summary = "List organizations", description = "Returns organizations available to the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organizations retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    OrganizationDetailsResponse getAll(
            @Parameter(hidden = true) @CurrentUserId long userId
    );

    @Operation(summary = "Get organization by id", description = "Returns details of a specific organization.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)
    })
    @GetMapping("/{id}")
    OrganizationDetailsResponse getById(
            @Parameter(hidden = true) @CurrentUserId UserPrincipal userPrincipal,
            @Parameter(description = "Organization identifier", example = "1") @PathVariable long id
    );

    @Operation(summary = "Create organization", description = "Creates an organization for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    OrganizationDetailsResponse create(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @Valid @RequestBody CreateOrganizationRequest request
    );

    @Operation(summary = "Update organization", description = "Updates an existing organization owned by the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)
    })
    @PutMapping("/{id}")
    OrganizationDetailsResponse update(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @Parameter(description = "Organization identifier", example = "1") @PathVariable long id,
            @Valid @RequestBody UpdateOrganizationRequest request
    );

    @Operation(summary = "Delete organization", description = "Deletes an organization owned by the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)
    })

    @DeleteMapping("/{id}")
    void delete(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @Parameter(description = "Organization identifier", example = "1") @PathVariable long id
    );
}
