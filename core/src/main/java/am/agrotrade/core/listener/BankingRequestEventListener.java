package am.agrotrade.core.listener;

import am.agrotrade.common.dto.banking.event.ContractCreateRequestEvent;
import am.agrotrade.core.service.impl.BankingIntegrationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class BankingRequestEventListener {

    private final BankingIntegrationProducer bankingIntegrationProducer;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleContractCreateRequest(ContractCreateRequestEvent event) {
        bankingIntegrationProducer.sendContractCreateRequest(event);
    }
}
