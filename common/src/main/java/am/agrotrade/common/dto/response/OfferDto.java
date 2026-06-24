package am.agrotrade.common.dto.response;

import java.math.BigDecimal;

public record OfferDto(

        String offerType,
        BigDecimal interestRate

) {
}
