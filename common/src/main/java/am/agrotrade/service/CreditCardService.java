package am.agrotrade.service;

import am.agrotrade.dto.creditCard.request.CreateCreditCardRequest;
import am.agrotrade.dto.creditCard.response.CardResponse;

public interface CreditCardService {

    void save(CreateCreditCardRequest creditCard);

    CardResponse findCreditCardByUserId(long userId);
}
