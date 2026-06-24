package am.agrotrade.common.dto.creditCard.request;

import java.time.LocalDateTime;

public record CreateCreditCardRequest(

        String cardNumber,
        String cardHolderName,
        String expiryDate,
        LocalDateTime createdAt


) {
}
