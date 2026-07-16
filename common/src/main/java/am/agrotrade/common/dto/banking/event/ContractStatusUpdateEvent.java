package am.agrotrade.common.dto.banking.event;

public record ContractStatusUpdateEvent(

        long finalContractId,
        String documentUrl

) {
}
