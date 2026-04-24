package am.agrotrade.core.client;

import am.agrotrade.common.dto.request.SendNotificationRequest;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;

public interface NotificationClient {

    void sendNotificationSettings(SendNotificationSettingsRequest request);

    void sendNotification(SendNotificationRequest request);
}