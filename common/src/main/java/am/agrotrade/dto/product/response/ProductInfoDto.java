package am.agrotrade.dto.product.response;

import am.agrotrade.model.enums.CategoryProduct;
import am.agrotrade.model.enums.ProductStatus;

import java.math.BigDecimal;

public record ProductInfoDto(

        long id,
        String name,
        String description,
        BigDecimal price,
        CategoryProduct category,
        ProductStatus status,
        long sellerId,
        String organizationName


) {
}
