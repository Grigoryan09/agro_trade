package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.common.dto.passport.response.PassportInfoResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Passport V1", description = "Passport management endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/user/passport")
public interface PassportV1API {

    @Operation(summary = "Get passport", description = "Returns passport details of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passport retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Passport not found", content = @Content)
    })
    @GetMapping()
    PassportInfoResponse get(
            @Parameter(hidden = true) @CurrentUserId long userId
    );

    @Operation(summary = "Create passport", description = "Creates passport details for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passport created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping()
    PassportInfoResponse create(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @Valid @RequestBody CreateAndUpdatePassportRequest request
    );

    @Operation(summary = "Update passport", description = "Updates passport details of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passport updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Passport not found", content = @Content)
    })
    @PutMapping()
    PassportInfoResponse update(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @Valid @RequestBody CreateAndUpdatePassportRequest request
    );

    @Operation(summary = "Delete passport", description = "Deletes passport details of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passport deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Passport not found", content = @Content)
    })
    @DeleteMapping()
    void delete(
            @Parameter(hidden = true) @CurrentUserId long userId
    );

}
