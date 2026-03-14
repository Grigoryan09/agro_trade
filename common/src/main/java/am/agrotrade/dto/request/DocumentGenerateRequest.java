package am.agrotrade.dto.request;

import am.agrotrade.dto.response.BankDto;
import am.agrotrade.dto.response.FinalContractDto;
import am.agrotrade.dto.response.OfferDto;
import am.agrotrade.dto.user.ClientInfoDto;

public record DocumentGenerateRequest(

        BankDto bankDto,
        OfferDto offerDto,
        FinalContractDto finalContractDto,
        ClientInfoDto clientInfoDto

) {
}
