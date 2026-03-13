package am.agrotrade.dto.bankingRequest.response;

import am.agrotrade.dto.product.response.BaseProductInfoDto;
import am.agrotrade.dto.user.response.BaseUserInfoDto;

import java.math.BigDecimal;

public record BaseInfoBankingRequestDto(

        long id,
        String bankingRequestType,
        BaseUserInfoDto user,
        String purpose,
        BigDecimal creditAmount,
        BaseProductInfoDto productInfoDto,
        String repaymentStatus,
        int repaymentPeriod,
        String repaymentType

) {
}
