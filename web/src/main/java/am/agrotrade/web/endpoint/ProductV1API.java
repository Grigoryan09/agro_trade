package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.dto.product.response.ProductInfoResponse;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API v1 for managing products in AgroTrade service.
 *
 * <p>Provides endpoints for product CRUD operations: listing, retrieval, creation, update, and deletion.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/products")
public interface ProductV1API {

    /**
     * Retrieves a paginated list of all active products (excluding deleted ones).
     *
     * @param pageable pagination and sorting parameters (page, size, sort)
     * @return page of {@link ProductInfoResponse}
     */
    @GetMapping
    Page<ProductInfoResponse> getAll(Pageable pageable);

    /**
     * Retrieves a paginated list of products belonging to the currently authenticated seller.
     *
     * @param sellerId ID of the currently authenticated seller (automatically resolved via {@link CurrentUserId})
     * @param pageable pagination and sorting parameters
     * @return page of {@link ProductInfoResponse} for the seller's products
     */
    @GetMapping("/my")
    Page<ProductInfoResponse> getAllBySeller(
            @CurrentUserId long sellerId,
            Pageable pageable
    );

    /**
     * Retrieves detailed information about a specific product by its ID.
     *
     * @param id the ID of the product
     * @return {@link ProductInfoResponse} containing product details
     */
    @GetMapping("/{id}")
    ProductInfoResponse getById(
            @PathVariable long id
    );

    /**
     * Creates a new product for the currently authenticated seller.
     *
     * @param sellerId ID of the currently authenticated seller
     * @param request  request body containing product creation data
     * @return the created {@link ProductInfoResponse}
     */
    @PostMapping
    ProductInfoResponse create(
            @CurrentUserId long sellerId,
            @Valid @RequestBody CreateProductRequest request
    );

    /**
     * Updates an existing product of the currently authenticated seller.
     *
     * @param sellerId ID of the currently authenticated seller
     * @param id       ID of the product to update
     * @param request  request body containing updated product data
     * @return the updated {@link ProductInfoResponse}
     */
    @PutMapping("/{id}")
    ProductInfoResponse update(
            @CurrentUserId long sellerId,
            @PathVariable long id,
            @Valid @RequestBody UpdateProductRequest request
    );

    /**
     * Deletes a product belonging to the currently authenticated seller (soft delete).
     *
     * @param sellerId ID of the currently authenticated seller
     * @param id       ID of the product to delete
     */
    @DeleteMapping("/{id}")
    void delete(
            @CurrentUserId long sellerId,
            @PathVariable long id
    );
}