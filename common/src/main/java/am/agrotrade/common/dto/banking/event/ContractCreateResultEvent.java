package am.agrotrade.common.dto.banking.event;

import java.math.BigDecimal;

public record ContractCreateResultEvent(

        long externalRequestId,
        long finalContractId,
        BigDecimal approvedAmount,
        int tenor,
        String tenorUnit,
        String offerType,
        BigDecimal interestRate,
        int maxDurationMonths,
        BigDecimal minAmount,
        String bankName,
        String bankPhoneNumber,
        String bankLicenseNumber,
        String productName,
        String productType

) {
}
