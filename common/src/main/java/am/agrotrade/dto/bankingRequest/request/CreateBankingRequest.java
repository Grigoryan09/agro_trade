package am.agrotrade.dto.bankingRequest.request;

import am.agrotrade.dto.product.response.ProductInfoResponse;
import am.agrotrade.model.enums.RepaymentType;

import java.math.BigDecimal;

public record CreateBankingRequest(

        String bankingRequestType,
        String purpose,
        BigDecimal creditAmount,
        ProductInfoResponse product,
        int repaymentPeriod,
        RepaymentType repaymentType

) {
}
