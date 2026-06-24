package am.agrotrade.core.service;

import am.agrotrade.common.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.common.dto.request.Request2Bank;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Handles creation and retrieval of banking requests.
 */
public interface BankingRequestService {

    /**
     * Creates a banking request.
     *
     * @param bankingRequest request payload
     */
    void save(CreateBankingRequest bankingRequest);

    /**
     * Deletes a banking request by identifier.
     *
     * @param bankingRequestId banking request identifier
     */
    void delete(long bankingRequestId);

    /**
     * Returns all banking requests.
     *
     * @param pageable paging parameters
     * @return banking request list
     */
    List<Request2Bank> findAll(Pageable pageable);

    /**
     * Returns a banking request by identifier.
     *
     * @param bankingRequestId banking request identifier
     * @return banking request data
     */
    Request2Bank findById(long bankingRequestId);
}
