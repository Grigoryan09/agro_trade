package am.agrotrade.dto.product.request;

import am.agrotrade.dto.user.response.AuthUserResponse;
import am.agrotrade.model.enums.CategoryProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateProductRequest(

        String name,
        String description,
        BigDecimal price,
        CategoryProduct category,
        LocalDateTime createdAt,
        AuthUserResponse user

) {
}
