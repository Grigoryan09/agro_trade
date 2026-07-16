package am.agrotrade.common.dto.user.request;

import am.agrotrade.common.enums.Role;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateUserRolesRequest(

        @NotEmpty(message = "At least one role must be provided")
        List<Role> roles

) {
}
