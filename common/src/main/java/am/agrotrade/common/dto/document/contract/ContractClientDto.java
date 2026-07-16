package am.agrotrade.common.dto.document.contract;

public record ContractClientDto(

        String fullName,
        String email,
        String phoneNumber,
        ContractPassportDto passportInfo

) {
}
