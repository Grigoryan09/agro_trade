package am.agrotrade.common.dto.request;

import am.agrotrade.common.dto.product.ProductDetailsDto;

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
