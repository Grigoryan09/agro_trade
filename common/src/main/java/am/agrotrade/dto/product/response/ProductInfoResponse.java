package am.agrotrade.dto.product.response;

import am.agrotrade.dto.user.SellerInfoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductInfoResponse(

        String name,
        String description,
        BigDecimal price,
        String category,
        String status,
        SellerInfoDto sellerInfoDto,
        LocalDateTime createdAt

) {
}
