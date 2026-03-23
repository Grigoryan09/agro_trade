package am.agrotrade.dto.user;

import am.agrotrade.dto.organization.OrganizationDetailsDto;

public record SellerInfoDto(

        String sellerId,
        String sellerName,
        OrganizationDetailsDto organization

) {
}
