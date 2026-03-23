package am.agrotrade.dto.bankingRequest.request;

import am.agrotrade.dto.product.response.ProductInfoDto;
import am.agrotrade.model.enums.RepaymentType;

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
