package am.agrotrade.service;

import am.agrotrade.dto.product.request.CreateProductRequest;
import am.agrotrade.dto.product.response.ProductInfoResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {

    void save(CreateProductRequest product);

    void delete(long productId);

    List<ProductInfoResponse> findAll(Pageable pageable);

    ProductInfoResponse findById(long productId);

    ProductInfoResponse findProductBySellerId(long sellerId, Pageable pageable);


}
