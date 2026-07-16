package am.agrotrade.common.dto.document.contract;

public record ContractDocumentResultEvent(

        long externalRequestId,
        long finalContractId,
        String fileName,
        String base64Document

) {
}
