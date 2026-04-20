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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

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

    @Override
    @Transactional
    public void delete(long sellerId, long productId) {
        Product product = productRepository.findBySellerIdAndProductId(sellerId, productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found or access denied"));

        product.setStatus(ProductStatus.DELETED);
    }

    @Override
    public List<ProductInfoDto> findAllByStatusNot(Pageable pageable) {
        return productRepository.findAllByStatusNot(ProductStatus.DELETED, pageable)
                .map(productMapper::toDto)
                .getContent();
    }

    @Override
    public List<ProductInfoDto> findAllBySeller(long userId, Pageable pageable) {
        return productRepository.findAllBySellerId(userId, pageable)
                .map(productMapper::toDto)
                .getContent();
    }

    @Override
    public List<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto)
                .getContent();
    }

    @Override
    public ProductInfoDto findById(long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found for ID: %d ".formatted(productId)
                ));
    }
}