package am.agrotrade.common.dto.request;

import am.agrotrade.common.dto.NotificationSettingsDTO;
import jakarta.validation.Valid;

public record SendNotificationSettingsRequest(
        @Valid NotificationSettingsDTO notificationSettingsDTO) {
}
