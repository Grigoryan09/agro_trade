package am.agrotrade.dto.bankingRequest.request;

import am.agrotrade.dto.product.response.BaseProductInfoDto;
import am.agrotrade.dto.user.response.AuthUserResponse;
import am.agrotrade.model.enums.BankingRequestType;
import am.agrotrade.model.enums.RepaymentType;

import java.math.BigDecimal;

public record CreateBankingRequest(

        BankingRequestType bankingRequestType,
        AuthUserResponse user,
        String purpose,
        BigDecimal creditAmount,
        BaseProductInfoDto product,
        int repaymentPeriod,
        RepaymentType repaymentType

) {
}
