package am.agrotrade.common.dto.document;

public record BuyerInfoDto(
        String fullName,
        String address,
        String email,
        String phoneNumber
) {
}
