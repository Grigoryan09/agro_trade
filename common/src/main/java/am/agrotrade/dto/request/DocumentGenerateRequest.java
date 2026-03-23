package am.agrotrade.dto.request;

import am.agrotrade.dto.PaymentRowDto;
import am.agrotrade.dto.response.BankDto;
import am.agrotrade.dto.response.FinalContractDto;
import am.agrotrade.dto.response.OfferDto;
import am.agrotrade.dto.user.ClientInfoDto;

import java.util.List;

public record DocumentGenerateRequest(

        BankDto bankDto,
        OfferDto offerDto,
        FinalContractDto finalContractDto,
        ClientInfoDto clientInfoDto,
        List<PaymentRowDto> paymentRowDtoList,
        String documentType

) {
}
