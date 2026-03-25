package am.agrotrade.core.service;

import am.agrotrade.common.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.common.dto.request.Request2Bank;

import java.awt.print.Pageable;
import java.util.List;

public interface BankingRequestService {

    void save(CreateBankingRequest bankingRequest);

    void delete(long bankingRequestId);

    List<Request2Bank> findAll(Pageable pageable);

    Request2Bank findById(long bankingRequestId);
}
