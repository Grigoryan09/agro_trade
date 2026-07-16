package am.agrotrade.common.dto.document;

public record SellerInfoDto(
        String fullName,
        String address,
        String email,
        String phoneNumber
) {
}
