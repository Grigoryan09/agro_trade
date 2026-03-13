package am.agrotrade.dto.organization.request;

public record CreateOrganizationRequest(

        long userId,
        String name,
        String licenseNumber,
        String address,
        String contactPhone,
        String email

) {
}
