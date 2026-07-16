package am.agrotrade.common.dto.user.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(

        @NotNull(message = "Active flag cannot be null")
        Boolean active

) {
}
