package am.agrotrade.service;

import am.agrotrade.dto.creditCard.request.CreateCreditCardRequest;
import am.agrotrade.dto.creditCard.response.CardResponse;
import am.agrotrade.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {

    void save(CreateCreditCardRequest creditCard);

    CardResponse findCreditCardByUserId(long userId);
}
