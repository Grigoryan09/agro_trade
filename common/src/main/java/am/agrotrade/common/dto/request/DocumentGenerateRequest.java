package am.agrotrade.common.dto.request;

import am.agrotrade.common.dto.PaymentRowDto;
import am.agrotrade.common.dto.response.BankDto;
import am.agrotrade.common.dto.response.FinalContractDto;
import am.agrotrade.common.dto.response.OfferDto;
import am.agrotrade.common.dto.user.ClientInfoDto;

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
