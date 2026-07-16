package am.agrotrade.common.dto.document.contract;

import am.agrotrade.common.dto.PaymentRowDto;

import java.util.List;

public record ContractDocumentGenerateEvent(

        long externalRequestId,
        long finalContractId,
        BankInfoDto bankDto,
        OfferInfoDto offerDto,
        ContractFinalDto finalContractDto,
        ContractClientDto clientInfoDto,
        List<PaymentRowDto> paymentRowDtoList,
        String documentType

) {
}
