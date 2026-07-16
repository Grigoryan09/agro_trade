package am.agrotrade.common.dto.document.contract;

import java.math.BigDecimal;

public record OfferInfoDto(

        BankInfoDto bank,
        String offerType,
        BigDecimal interestRate,
        int maxDurationMonths,
        BigDecimal minAmount

) {
}
