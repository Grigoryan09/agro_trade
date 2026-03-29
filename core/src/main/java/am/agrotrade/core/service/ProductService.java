package am.agrotrade.core.service;

import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.response.ProductInfoDto;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {

    void save(CreateProductRequest product);

    void delete(long productId);

    List<ProductInfoDto> findAll(Pageable pageable);

    ProductInfoDto findById(long productId);

    ProductInfoDto findProductBySellerId(long sellerId, Pageable pageable);


}
