package am.agrotrade.service;

import am.agrotrade.model.CreditCard;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {

    void save(CreditCard creditCard);

    void delete(long creditCardId);

    List<CreditCard> findAll();

    Optional<CreditCard> findById(long creditCardId);
}
