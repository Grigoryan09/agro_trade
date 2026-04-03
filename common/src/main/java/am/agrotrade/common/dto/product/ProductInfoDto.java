package am.agrotrade.common.dto.product;

import am.agrotrade.common.dto.user.SellerInfoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductInfoDto(

        long id,
        String name,
        String description,
        BigDecimal price,
        String category,
        String status,
        SellerInfoDto sellerInfoDto,
        LocalDateTime createdAt

) {
}
