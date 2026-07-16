package am.agrotrade.core.service;

import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Manages product lifecycle operations and product queries.
 */
public interface ProductService {

    /**
     * Creates a product for the specified seller.
     *
     * @param sellerId seller identifier
     * @param product product payload
     * @return created product data
     */
    ProductInfoDto create(long sellerId, CreateProductRequest product);

    /**
     * Updates a product owned by the specified seller.
     *
     * @param sellerId seller identifier
     * @param productId product identifier
     * @param request updated product payload
     * @return updated product data
     */
    ProductInfoDto update(long sellerId, long productId, UpdateProductRequest request);

    /**
     * Deletes a product owned by the specified seller.
     *
     * @param sellerId seller identifier
     * @param productId product identifier
     */
    void delete(long sellerId, long productId);

    /**
     * Returns all products excluding products with the filtered status.
     *
     * @param pageable paging parameters
     * @return product list
     */
    List<ProductInfoDto> findAllByStatusNot(Pageable pageable);

    /**
     * Returns products created by the specified seller.
     *
     * @param sellerId seller identifier
     * @param pageable paging parameters
     * @return seller product list
     */
    List<ProductInfoDto> findAllBySeller(long sellerId, Pageable pageable);

    /**
     * Returns a product by identifier.
     *
     * @param productId product identifier
     * @return product data
     */
    ProductInfoDto findById(long productId);
}
