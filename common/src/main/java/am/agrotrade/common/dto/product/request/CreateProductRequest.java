package am.agrotrade.common.dto.product.request;

import am.agrotrade.common.enums.CategoryProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateProductRequest(

        String name,
        String description,
        BigDecimal price,
        CategoryProduct category,
        LocalDateTime createdAt

) {
}
