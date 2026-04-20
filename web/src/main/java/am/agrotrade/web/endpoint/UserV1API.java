package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.common.dto.user.response.BaseUserInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User V1", description = "User profile endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/user")
public interface UserV1API {

    @Operation(summary = "Get current user", description = "Returns profile data of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping()
    BaseUserInfoResponse get(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal user);

    @Operation(summary = "Update current user", description = "Updates profile data of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PutMapping()
    BaseUserInfoResponse update(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody UpdateUserRequest request);

    @Operation(summary = "Change password", description = "Changes the password of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PutMapping("/change-password")
    BaseUserInfoResponse changePassword(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody ChangePasswordRequest request
    );
}
