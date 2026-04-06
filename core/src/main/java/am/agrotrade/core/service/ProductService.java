package am.agrotrade.core.service;

import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing products in AgroTrade application.
 *
 * <p>Provides business logic for product CRUD operations, including creation,
 * updating, deletion and various search methods with pagination support.
 */
public interface ProductService {

    /**
     * Creates a new product for the specified seller.
     *
     * @param sellerId ID of the seller who creates the product
     * @param product  request containing product details
     * @return {@link ProductInfoDto} of the created product
     */
    ProductInfoDto create(long sellerId, CreateProductRequest product);

    /**
     * Updates an existing product of the specified seller.
     *
     * @param sellerId  ID of the seller who owns the product
     * @param productId ID of the product to update
     * @param request   request containing updated product data
     * @return {@link ProductInfoDto} of the updated product
     */
    ProductInfoDto update(long sellerId, long productId, UpdateProductRequest request);

    /**
     * Deletes a product (usually soft delete) for the specified seller.
     *
     * @param sellerId  ID of the seller who owns the product
     * @param productId ID of the product to delete
     */
    void delete(long sellerId, long productId);

    /**
     * Retrieves all products excluding deleted ones (soft delete).
     *
     * @param pageable pagination and sorting parameters
     * @return paginated list of {@link ProductInfoDto}
     */
    Page<ProductInfoDto> findAllByStatusNot(Pageable pageable);

    /**
     * Retrieves all products belonging to a specific seller.
     *
     * @param sellerId ID of the seller
     * @param pageable pagination and sorting parameters
     * @return paginated list of {@link ProductInfoDto}
     */
    Page<ProductInfoDto> findAllBySeller(long sellerId, Pageable pageable);

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable pagination and sorting parameters
     * @return paginated list of {@link ProductInfoDto}
     */
    Page<ProductInfoDto> findAll(Pageable pageable);

    /**
     * Retrieves a product by its ID.
     *
     * @param productId ID of the product
     * @return {@link ProductInfoDto} containing product details
     * @throws RuntimeException if product not found
     */
    ProductInfoDto findById(long productId);
}
