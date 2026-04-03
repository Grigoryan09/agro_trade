package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.enums.ProductStatus;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.ProductMapper;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.ProductRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of {@link ProductService} for managing products.
 *
 * <p>Handles business logic for product creation, update, deletion (soft delete),
 * and retrieval with proper seller ownership validation.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    /**
     * Creates a new product for the specified seller.
     *
     * @param sellerId the ID of the seller creating the product
     * @param request  the product creation data
     * @return the created product as {@link ProductInfoDto}
     * @throws ResourceNotFoundException if the seller is not found
     */
    @Override
    @Transactional
    public ProductInfoDto create(long sellerId, CreateProductRequest request) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productMapper.toEntity(request);

        product.setStatus(ProductStatus.AVAILABLE);
        product.setCreatedAt(LocalDateTime.now());
        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    /**
     * Updates an existing product. Only the owner (seller) can update their product.
     *
     * @param sellerId the ID of the seller trying to update the product
     * @param productId the ID of the product to update
     * @param request the updated product data
     * @return the updated product as {@link ProductInfoDto}
     * @throws ResourceNotFoundException if the product is not found or does not belong to the seller
     */
    @Override
    @Transactional
    public ProductInfoDto update(long sellerId, long productId, UpdateProductRequest request) {
        Product product =
                productRepository.findBySellerIdAndProductId(sellerId, productId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Product not found for ID: %d and seller ID: %d".formatted(productId, sellerId)
                        ));

        productMapper.updateEntityFromRequest(request, product);

        return productMapper.toDto(product);
    }

    /**
     * Soft deletes a product by changing its status to {@link ProductStatus#DELETED}.
     * Only the owner (seller) can delete their product.
     *
     * @param sellerId the ID of the seller trying to delete the product
     * @param productId the ID of the product to delete
     * @throws ResourceNotFoundException if the product is not found or does not belong to the seller
     */
    @Override
    @Transactional
    public void delete(long sellerId, long productId) {
        Product product = productRepository.findBySellerIdAndProductId(sellerId, productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found or access denied"));

        product.setStatus(ProductStatus.DELETED);
    }

    /**
     * Retrieves a paginated list of all active products (excludes products with status {@link ProductStatus#DELETED}).
     *
     * @param pageable pagination and sorting parameters
     * @return page of {@link ProductInfoDto}
     */
    @Override
    public Page<ProductInfoDto> findAllByStatusNot(Pageable pageable) {
        return productRepository.findAllByStatusNot(ProductStatus.DELETED, pageable)
                .map(productMapper::toDto);
    }

    /**
     * Retrieves a paginated list of products belonging to a specific seller.
     *
     * @param userId   the ID of the seller
     * @param pageable pagination and sorting parameters
     * @return page of {@link ProductInfoDto} for the seller
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductInfoDto> findAllBySeller(long userId, Pageable pageable) {
        return productRepository.findAllBySellerId(userId, pageable)
                .map(productMapper::toDto);
    }

    /**
     * Retrieves all products with pagination (for internal/admin use).
     * Note: This method returns all products including deleted ones.
     *
     * @param pageable pagination and sorting parameters
     * @return page of {@link ProductInfoDto}
     */
    @Override
    public Page<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    /**
     * Retrieves a single product by its ID.
     *
     * @param productId the ID of the product
     * @return {@link ProductInfoDto} containing product details
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    @Transactional(readOnly = true)
    public ProductInfoDto findById(long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found for ID: %d ".formatted(productId)
                ));
    }
}