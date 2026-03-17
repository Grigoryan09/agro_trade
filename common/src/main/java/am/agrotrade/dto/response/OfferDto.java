package am.agrotrade.dto.response;

import java.math.BigDecimal;

public record OfferDto(

        String offerType,
        BigDecimal interestRate

) {
}
