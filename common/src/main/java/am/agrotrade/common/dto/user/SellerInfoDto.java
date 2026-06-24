package am.agrotrade.common.dto.user;

import am.agrotrade.common.dto.organization.OrganizationDetailsDto;

public record SellerInfoDto(

        String sellerId,
        String sellerName,
        OrganizationDetailsDto organization

) {
}
