package am.agrotrade.core.service;

import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductInfoDto create(long sellerId, CreateProductRequest product);

    ProductInfoDto update(long sellerId, long productId, UpdateProductRequest request);

    void delete(long sellerId, long productId);

    Page<ProductInfoDto> findAllByStatusNot(Pageable pageable);

    Page<ProductInfoDto> findAllBySeller(long sellerId, Pageable pageable);

    Page<ProductInfoDto> findAll(Pageable pageable);

    ProductInfoDto findById(long productId);


}
