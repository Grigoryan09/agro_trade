package am.agrotrade.common.dto.document.contract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContractFinalDto(

        BigDecimal approvedAmount,
        int approvedPeriod,
        ContractProductDto productDto,
        LocalDateTime createdAt

) {
}
