package am.agrotrade.core.client;

import am.agrotrade.common.dto.request.OrderNotificationRequest;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;
import am.agrotrade.common.dto.request.VerifyNotificationRequest;
import am.agrotrade.common.dto.request.WelcomeNotificationRequest;

public interface NotificationClient {

    void sendNotificationSettings(SendNotificationSettingsRequest request);

    void sendVerifyEmail(VerifyNotificationRequest request);

    void sendOrderOpenedEmail(OrderNotificationRequest request);

    void sendWelcomeEmail(WelcomeNotificationRequest request);
}
