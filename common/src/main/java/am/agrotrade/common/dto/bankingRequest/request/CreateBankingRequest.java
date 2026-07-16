package am.agrotrade.common.dto.bankingRequest.request;

import am.agrotrade.common.enums.BankingRequestType;
import am.agrotrade.common.enums.TermUnit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateBankingRequest(

        @NotNull(message = "Banking request type is required")
        BankingRequestType bankingRequestType,

        @NotNull(message = "Offer id is required")
        @Positive(message = "Offer id must be positive")
        Long offerId,

        String purpose,

        @NotNull(message = "Approved amount is required")
        @Positive(message = "Approved amount must be positive")
        BigDecimal approvedAmount,

        @Positive(message = "Tenor must be positive")
        @Min(value = 1, message = "Tenor must be at least 1")
        int tenor,

        @NotNull(message = "Term unit is required")
        TermUnit termUnit,

        String productName,

        String productType

) {
}
