package am.agrotrade.common.dto.bankingRequest.response;

import java.util.List;

public record BankingRequestResponse(

        List<BankingRequestInfoDto> requests

) {
}
