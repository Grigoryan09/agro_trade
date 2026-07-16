package am.agrotrade.common.dto.user;

import am.agrotrade.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserForAdminDto extends BaseUserInfoDto {

    private long id;
    private List<Role> roles;
    private boolean active;
}
