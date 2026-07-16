package am.agrotrade.common.dto.document;

public record ManagerInfoDto(
        String fullName,
        String address,
        String email,
        String phoneNumber
) {
}
