package am.agrotrade.common.dto.bankingRequest;

import java.math.BigDecimal;

public record BankingRequestSaveDto(

        String type,
        String purpose,
        BigDecimal creditAmount
) {
}
