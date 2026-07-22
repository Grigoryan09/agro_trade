package am.agrotrade.core.service;

import am.agrotrade.common.dto.banking.event.ContractCreateResultEvent;
import am.agrotrade.common.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.common.dto.bankingRequest.response.BankingRequestInfoDto;
import am.agrotrade.common.dto.document.contract.ContractDocumentGenerateEvent;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankingRequestService {

    BankingRequestInfoDto create(long userId, CreateBankingRequest request);

    void delete(long bankingRequestId);

    List<BankingRequestInfoDto> findByUserId(long userId, Pageable pageable);

    BankingRequestInfoDto findById(long bankingRequestId);

    ContractDocumentGenerateEvent onContractCreated(ContractCreateResultEvent event);

    void completeContract(long bankingRequestId);
}
