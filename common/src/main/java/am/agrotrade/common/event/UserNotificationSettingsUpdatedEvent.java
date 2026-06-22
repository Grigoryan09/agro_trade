package am.agrotrade.common.event;

import am.agrotrade.common.dto.NotificationSettingsEvent;

public record UserNotificationSettingsUpdatedEvent(
        NotificationSettingsEvent notificationSettingsEvent
) {}