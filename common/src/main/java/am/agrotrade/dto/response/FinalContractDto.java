package am.agrotrade.dto.response;

import am.agrotrade.dto.product.ProductDetailsDto;

import java.time.LocalDateTime;

public record FinalContractDto(

        String approvedAmount,
        int approvedPeriod,
        ProductDetailsDto productDetailsDto,
        String clientPhoneNumber,
        LocalDateTime createdAt

) {
}
