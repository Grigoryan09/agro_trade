package am.agrotrade.core.service;

import am.agrotrade.common.dto.creditCard.request.CreateCreditCardRequest;
import am.agrotrade.common.dto.creditCard.response.CardDto;

/**
 * Handles credit card persistence and lookup.
 */
public interface CreditCardService {

    /**
     * Saves credit card data.
     *
     * @param creditCard credit card payload
     */
    void save(CreateCreditCardRequest creditCard);

    /**
     * Returns a user's credit card data.
     *
     * @param userId user identifier
     * @return credit card data
     */
    CardDto findCreditCardByUserId(long userId);
}
