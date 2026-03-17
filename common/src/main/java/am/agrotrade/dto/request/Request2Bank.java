package am.agrotrade.dto.request;

import am.agrotrade.dto.product.ProductDetailsDto;

import java.math.BigDecimal;

public record Request2Bank(

        String bankingRequestType,
        String purpose,
        BigDecimal creditAmount,
        ProductDetailsDto productDetailsDto,
        int repaymentPeriod,
        String repaymentType

) {
}
