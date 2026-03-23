package am.agrotrade.dto.product.request;

import am.agrotrade.model.enums.ProductStatus;

import java.math.BigDecimal;

public record UpdateProductRequest(

        String name,
        String description,
        BigDecimal price,
        ProductStatus status,
        long sellerId

) {
}
