package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.news.response.NewsResponse;
import am.agrotrade.common.dto.order.response.OrderResponse;
import am.agrotrade.common.dto.organization.response.OrganizationDetailsResponse;
import am.agrotrade.common.dto.passport.response.PassportInfoResponse;
import am.agrotrade.common.dto.product.response.ProductInfoResponse;
import am.agrotrade.common.dto.user.request.UpdateUserRolesRequest;
import am.agrotrade.common.dto.user.request.UpdateUserStatusRequest;
import am.agrotrade.common.dto.user.response.UsersForAdminResponse;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.common.enums.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin V1", description = "Administrative oversight endpoints. Requires the ADMIN authority.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public interface AdminV1API {

    // ---------------------------------------------------------------- Orders

    @Operation(summary = "List all orders",
            description = "Returns a paginated list of every order in the system, optionally filtered by status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/orders")
    OrderResponse getAllOrders(
            @Parameter(description = "Optional order status filter", example = "PENDING")
            @RequestParam(required = false) OrderStatus orderStatus,
            @ParameterObject Pageable pageable);

    @Operation(summary = "Delete any order",
            description = "Soft-deletes any order regardless of owner by setting its status to DELETED.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @DeleteMapping("/orders/{id}")
    void deleteOrder(@Parameter(description = "Order identifier", example = "1") @PathVariable long id);

    // ----------------------------------------------------------------- Users

    @Operation(summary = "List all users",
            description = "Returns a paginated list of all users, optionally filtered by role and a free-text search.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/users")
    UsersForAdminResponse getAllUsers(
            @Parameter(description = "Optional role filter", example = "SELLER")
            @RequestParam(required = false) Role role,
            @Parameter(description = "Optional search over username, email, name and surname")
            @RequestParam(required = false) String search,
            @ParameterObject Pageable pageable);

    @Operation(summary = "Get user by id", description = "Returns a single user by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/users/{id}")
    UsersForAdminResponse getUserById(
            @Parameter(description = "User identifier", example = "1") @PathVariable long id);

    @Operation(summary = "Update user roles", description = "Replaces the roles assigned to a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User roles updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/users/{id}/roles")
    UsersForAdminResponse updateUserRoles(
            @Parameter(description = "User identifier", example = "1") @PathVariable long id,
            @Valid @RequestBody UpdateUserRolesRequest request);

    @Operation(summary = "Update user status",
            description = "Enables or disables (verifies) a user account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/users/{id}/status")
    UsersForAdminResponse updateUserStatus(
            @Parameter(description = "User identifier", example = "1") @PathVariable long id,
            @Valid @RequestBody UpdateUserStatusRequest request);

    @Operation(summary = "Get a user's passport",
            description = "Returns passport (PII) data for the specified user. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passport retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Passport not found", content = @Content)
    })
    @GetMapping("/users/{id}/passport")
    PassportInfoResponse getUserPassport(
            @Parameter(description = "User identifier", example = "1") @PathVariable long id);

    // -------------------------------------------------------------- Products

    @Operation(summary = "List all products",
            description = "Returns a paginated list of every product in the system regardless of owner or status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/products")
    ProductInfoResponse getAllProducts(@ParameterObject Pageable pageable);

    @Operation(summary = "Delete any product",
            description = "Soft-deletes any product regardless of owner.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/products/{id}")
    void deleteProduct(@Parameter(description = "Product identifier", example = "1") @PathVariable long id);

    // ------------------------------------------------------------------ News

    @Operation(summary = "List all news",
            description = "Returns a paginated list of every news entry in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/news")
    NewsResponse getAllNews(@ParameterObject Pageable pageable);

    @Operation(summary = "Delete any news", description = "Deletes any news entry regardless of author.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "News not found", content = @Content)
    })
    @DeleteMapping("/news/{id}")
    void deleteNews(@Parameter(description = "News identifier", example = "1") @PathVariable long id);

    // --------------------------------------------------------- Organizations

    @Operation(summary = "List all organizations",
            description = "Returns a paginated list of every organization in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organizations retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/organizations")
    OrganizationDetailsResponse getAllOrganizations(@ParameterObject Pageable pageable);
}
