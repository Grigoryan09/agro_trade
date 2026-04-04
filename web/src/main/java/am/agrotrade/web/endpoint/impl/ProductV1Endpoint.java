package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.dto.product.response.ProductInfoResponse;
import am.agrotrade.core.service.ProductService;
import am.agrotrade.web.endpoint.ProductV1API;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Component
public class ProductV1Endpoint implements ProductV1API {

    private final ProductService productService;

    @Override
    public Page<ProductInfoResponse> getAll(Pageable pageable) {
        return productService.findAllByStatusNot(pageable).map(ProductInfoResponse::new);
    }

    @Override
    public Page<ProductInfoResponse> getAllBySeller(long sellerId, Pageable pageable) {
        return productService.findAllBySeller(sellerId, pageable).map(ProductInfoResponse::new);
    }

    @Override
    public ProductInfoResponse getById(long id) {
        return new ProductInfoResponse(productService.findById(id));
    }

    @Override
    public ProductInfoResponse create(long sellerId, CreateProductRequest request) {
        return new ProductInfoResponse(productService.create(sellerId, request));
    }

    @Override
    public ProductInfoResponse update(long sellerId, long id, UpdateProductRequest request) {
        return new ProductInfoResponse(productService.update(sellerId, id, request));
    }

    @Override
    public void delete(@CurrentUserId long sellerId,
                       @PathVariable long id) {
        productService.delete(sellerId, id);
    }
}
