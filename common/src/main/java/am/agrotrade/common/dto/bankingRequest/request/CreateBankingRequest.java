package am.agrotrade.common.dto.bankingRequest.request;

import am.agrotrade.common.dto.product.response.ProductInfoDto;
import am.agrotrade.common.enums.RepaymentType;

import java.math.BigDecimal;

public record CreateBankingRequest(

        String bankingRequestType,
        String purpose,
        BigDecimal creditAmount,
        ProductInfoDto product,
        int repaymentPeriod,
        RepaymentType repaymentType

) {
}
