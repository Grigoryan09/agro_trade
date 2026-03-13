package am.agrotrade.dto.creditCard.request;

import am.agrotrade.dto.user.response.AuthUserResponse;

import java.time.LocalDateTime;

public record CreateCreditCardRequest(

        AuthUserResponse user,
        String cardNumber,
        String cardHolderName,
        String expiryDate,
        LocalDateTime createdAt


) {
}
