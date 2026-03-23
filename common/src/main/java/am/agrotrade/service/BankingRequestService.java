package am.agrotrade.service;

import am.agrotrade.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.dto.request.Request2Bank;

import java.awt.print.Pageable;
import java.util.List;

public interface BankingRequestService {

    void save(CreateBankingRequest bankingRequest);

    void delete(long bankingRequestId);

    List<Request2Bank> findAll(Pageable pageable);

    Request2Bank findById(long bankingRequestId);
}
