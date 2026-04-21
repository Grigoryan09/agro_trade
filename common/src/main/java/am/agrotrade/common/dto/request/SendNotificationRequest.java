package am.agrotrade.common.dto.request;

import am.agrotrade.common.dto.OrderOpenedDto;
import am.agrotrade.common.enums.EmailType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SendNotificationRequest(

        @NotEmpty(message = "User IDs list must not be empty")
        List<Long> userIds,

        String code,

        @NotNull(message = "EmailType is required")
        EmailType emailType,

        OrderOpenedDto orderOpenedDto
) {
}