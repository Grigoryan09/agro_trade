package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.common.dto.bankingRequest.response.BankingRequestResponse;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Banking Request V1", description = "Credit/leasing request endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/banking-requests")
public interface BankingRequestV1API {

    @Operation(summary = "Create banking request",
            description = "Opens a credit/leasing request and starts the contract generation flow.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banking request created"),
            @ApiResponse(responseCode = "400", description = "Invalid request or missing passport data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    BankingRequestResponse create(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @Valid @RequestBody CreateBankingRequest request);

    @Operation(summary = "List my banking requests",
            description = "Returns the authenticated user's banking requests.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banking requests retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/my")
    BankingRequestResponse getMy(
            @Parameter(hidden = true) @CurrentUserId long userId,
            @ParameterObject Pageable pageable);

    @Operation(summary = "Get banking request by id",
            description = "Returns a single banking request.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banking request found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Banking request not found", content = @Content)
    })
    @GetMapping("/{id}")
    BankingRequestResponse getById(
            @Parameter(description = "Banking request id", example = "1") @PathVariable long id);

    @Operation(summary = "Delete banking request",
            description = "Deletes a banking request.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banking request deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Banking request not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@Parameter(description = "Banking request id", example = "1") @PathVariable long id);
}
