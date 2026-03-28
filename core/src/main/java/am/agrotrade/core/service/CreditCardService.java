package am.agrotrade.core.service;

import am.agrotrade.common.dto.creditCard.request.CreateCreditCardRequest;
import am.agrotrade.common.dto.creditCard.response.CardDto;

public interface CreditCardService {

    void save(CreateCreditCardRequest creditCard);

    CardDto findCreditCardByUserId(long userId);
}
