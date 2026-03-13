package am.agrotrade.service;

import am.agrotrade.dto.product.request.CreateProductRequest;
import am.agrotrade.dto.product.response.BaseProductInfoDto;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {

    void save(CreateProductRequest product);

    void delete(long productId);

    List<BaseProductInfoDto> findAll(Pageable pageable);

    BaseProductInfoDto findById(long productId);

    BaseProductInfoDto findProductBySellerId(long sellerId,  Pageable pageable);


}
