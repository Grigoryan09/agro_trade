package am.agrotrade.common.dto.bankingRequest.response;

import java.math.BigDecimal;

public record BankingRequestInfoDto(

        long id,
        String type,
        String status,
        BigDecimal approvedAmount,
        int tenor

) {
}
