package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.dto.news.response.NewsResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "News V1", description = "News management endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/news")
public interface NewsV1API {

    @Operation(summary = "List news", description = "Returns a paginated list of all news.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News list retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    NewsResponse getAll(@ParameterObject Pageable pageable);

    @Operation(summary = "List my news", description = "Returns a paginated list of news created by the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User news list retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/my")
    NewsResponse getMyNews(
            @Parameter(hidden = true) @CurrentUserId long authorId,
            @ParameterObject Pageable pageable
    );

    @Operation(summary = "Get news by id", description = "Returns detailed information about a single news item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "News not found", content = @Content)
    })
    @GetMapping("/{id}")
    NewsResponse getById(@Parameter(description = "News identifier", example = "1") @PathVariable long id);

    @Operation(summary = "Create news",
            description = "Creates a news item. Requires the OPERATOR or ADMIN authority.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMIN')")
    @PostMapping
    NewsResponse createNews(
            @Parameter(hidden = true) @CurrentUserId long authorId,
            @Valid @RequestBody CreateNewsRequest createNewsRequest
    );

    @Operation(summary = "Update news",
            description = "Updates any existing news item. Requires the OPERATOR or ADMIN authority.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "News not found", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMIN')")
    @PutMapping("/{id}")
    NewsResponse updateNews(
            @Parameter(description = "News identifier", example = "1") @PathVariable long id,
            @Valid @RequestBody CreateNewsRequest request
    );

    @Operation(summary = "Delete news",
            description = "Deletes any news item. Requires the OPERATOR or ADMIN authority.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "News deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "News not found", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    void delete(
            @Parameter(description = "News identifier", example = "1") @PathVariable long id
    );
}
