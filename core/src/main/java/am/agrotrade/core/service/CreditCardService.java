package am.agrotrade.core.service;

import am.agrotrade.common.dto.creditCard.request.CreateCreditCardRequest;
import am.agrotrade.common.dto.creditCard.response.CardResponse;

public interface CreditCardService {

    void save(CreateCreditCardRequest creditCard);

    CardResponse findCreditCardByUserId(long userId);
}
