package am.agrotrade.common.dto.request;

import am.agrotrade.common.dto.NotificationSettingsEvent;
import jakarta.validation.Valid;

public record SendNotificationSettingsRequest(
        @Valid NotificationSettingsEvent notificationSettingsEvent) {
}
