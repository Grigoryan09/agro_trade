package am.agrotrade.dto.response;

import am.agrotrade.dto.product.ProductDetailsDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FinalContractDto(

        BigDecimal approvedAmount,
        int approvedPeriod,
        ProductDetailsDto productDetailsDto,
        LocalDateTime createdAt

) {
}
