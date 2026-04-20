package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.dto.product.response.ProductInfoResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product V1", description = "Product management endpoints.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/agro-trade-service/api/v1/products")
public interface ProductV1API {

    @Operation(summary = "List products", description = "Returns a paginated list of active products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    ProductInfoResponse getAll(@ParameterObject Pageable pageable);

    @Operation(summary = "List my products", description = "Returns products created by the authenticated seller.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seller products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("/my")
    ProductInfoResponse getAllBySeller(
            @Parameter(hidden = true) @CurrentUserId long sellerId,
            @ParameterObject Pageable pageable
    );

    @Operation(summary = "Get product by id", description = "Returns detailed information about a single product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{id}")
    ProductInfoResponse getById(
            @Parameter(description = "Product identifier", example = "1") @PathVariable long id
    );

    @Operation(summary = "Create product", description = "Creates a product for the authenticated seller.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    ProductInfoResponse create(
            @Parameter(hidden = true) @CurrentUserId long sellerId,
            @Valid @RequestBody CreateProductRequest request
    );

    @Operation(summary = "Update product", description = "Updates a product owned by the authenticated seller.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PutMapping("/{id}")
    ProductInfoResponse update(
            @Parameter(hidden = true) @CurrentUserId long sellerId,
            @Parameter(description = "Product identifier", example = "1") @PathVariable long id,
            @Valid @RequestBody UpdateProductRequest request
    );

    @Operation(summary = "Delete product", description = "Deletes a product owned by the authenticated seller.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(
            @Parameter(hidden = true) @CurrentUserId long sellerId,
            @Parameter(description = "Product identifier", example = "1") @PathVariable long id
    );
}
