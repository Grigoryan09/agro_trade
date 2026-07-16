package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.common.dto.bankingRequest.response.BankingRequestResponse;
import am.agrotrade.core.service.BankingRequestService;
import am.agrotrade.web.endpoint.BankingRequestV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BankingRequestV1Endpoint implements BankingRequestV1API {

    private final BankingRequestService bankingRequestService;

    @Override
    public BankingRequestResponse create(long userId, CreateBankingRequest request) {
        return new BankingRequestResponse(List.of(bankingRequestService.create(userId, request)));
    }

    @Override
    public BankingRequestResponse getMy(long userId, Pageable pageable) {
        return new BankingRequestResponse(bankingRequestService.findByUserId(userId, pageable));
    }

    @Override
    public BankingRequestResponse getById(long id) {
        return new BankingRequestResponse(List.of(bankingRequestService.findById(id)));
    }

    @Override
    public void delete(long id) {
        bankingRequestService.delete(id);
    }
}
