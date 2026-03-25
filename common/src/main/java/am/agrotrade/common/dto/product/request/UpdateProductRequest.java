package am.agrotrade.common.dto.product.request;

import am.agrotrade.common.enums.ProductStatus;

import java.math.BigDecimal;

public record UpdateProductRequest(

        String name,
        String description,
        BigDecimal price,
        ProductStatus status,
        long sellerId

) {
}
