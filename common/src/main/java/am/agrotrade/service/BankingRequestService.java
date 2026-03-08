package am.agrotrade.service;

import am.agrotrade.model.BankingRequest;

import java.util.List;
import java.util.Optional;

public interface BankingRequestService {

    void save(BankingRequest bankingRequest);

    void delete(long bankingRequestId);

    List<BankingRequest> findAll();

    Optional<BankingRequest> findById(long bankingRequestId);
}
