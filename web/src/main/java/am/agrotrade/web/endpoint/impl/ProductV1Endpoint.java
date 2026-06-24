package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.dto.product.response.ProductInfoResponse;
import am.agrotrade.core.service.ProductService;
import am.agrotrade.web.endpoint.ProductV1API;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductV1Endpoint implements ProductV1API {

    private final ProductService productService;

    @Override
    public ProductInfoResponse getAll(Pageable pageable) {
        return new ProductInfoResponse(productService.findAll(pageable));
    }

    @Override
    public ProductInfoResponse getAllBySeller(long sellerId, Pageable pageable) {
        return new ProductInfoResponse(productService.findAllBySeller(sellerId, pageable));
    }

    @Override
    public ProductInfoResponse getById(long id) {
        return new ProductInfoResponse(List.of(productService.findById(id)));
    }

    @Override
    public ProductInfoResponse create(long sellerId, CreateProductRequest request) {
        return new ProductInfoResponse(List.of(productService.create(sellerId, request)));
    }

    @Override
    public ProductInfoResponse update(long sellerId, long id, UpdateProductRequest request) {
        return new ProductInfoResponse(List.of(productService.update(sellerId, id, request)));
    }

    @Override
    public void delete(@CurrentUserId long sellerId,
                       @PathVariable long id) {
        productService.delete(sellerId, id);
    }
}
