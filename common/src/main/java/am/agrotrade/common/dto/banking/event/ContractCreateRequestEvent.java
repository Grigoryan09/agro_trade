package am.agrotrade.common.dto.banking.event;

import java.math.BigDecimal;

public record ContractCreateRequestEvent(

        long externalRequestId,
        long offerId,
        BigDecimal approvedAmount,
        int tenor,
        String termUnit,
        String productName,
        String productType,
        String clientPhoneNumber

) {
}
