package am.agrotrade.service;

import am.agrotrade.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.dto.bankingRequest.response.BaseInfoBankingRequestDto;

import java.awt.print.Pageable;
import java.util.List;

public interface BankingRequestService {

    void save(CreateBankingRequest bankingRequest);

    void delete(long bankingRequestId);

    List<BaseInfoBankingRequestDto> findAll(Pageable pageable);

    BaseInfoBankingRequestDto findById(long bankingRequestId);
}
