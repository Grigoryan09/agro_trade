package am.agrotrade.common.event;

import am.agrotrade.common.dto.NotificationSettingsDTO;

public record UserNotificationSettingsUpdatedEvent(
        NotificationSettingsDTO notificationSettingsDTO
) {}