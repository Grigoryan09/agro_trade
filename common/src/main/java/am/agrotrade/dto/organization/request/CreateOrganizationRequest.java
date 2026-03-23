package am.agrotrade.dto.organization.request;

public record CreateOrganizationRequest(

        String name,
        String licenseNumber,
        String address,
        String contactPhone,
        String email

) {
}
