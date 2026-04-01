package am.agrotrade.common.dto.organization;

import am.agrotrade.common.dto.user.BaseUserInfoDto;

public record OrganizationDetailsDto(

        String name,
        String licenseNumber,
        String address,
        String contactNumber,
        String email,
        BaseUserInfoDto userInfoDto

) {

}
