package am.agrotrade.core.client;

import am.agrotrade.common.dto.request.SendNotificationRequest;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;
import am.agrotrade.core.exception.NotificationException;
import am.agrotrade.core.properties.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestTemplate restTemplate;
    private final NotificationProperties properties;

    private static final String NOTIFICATION_SETTINGS_SEND_PATH = "/notification-service/api/v1/notifications/settings/save";
    private static final String NOTIFICATION_SEND_PATH = "/notification-service/api/v1/notifications/send";


    public void send(String path, Object request) {
        String url = properties.url() + path;

        try {
            restTemplate.postForObject(url, request, Void.class);

        } catch (HttpStatusCodeException ex) {
            throw new NotificationException(
                    "Notification service error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString()
            );

        } catch (ResourceAccessException ex) {
            throw new NotificationException(
                    "Notification service is unavailable"
            );

        } catch (Exception ex) {
            throw new NotificationException(
                    "Unexpected error while calling notification service: " + ex.getMessage()
            );
        }
    }

    public void sendNotificationSettings(SendNotificationSettingsRequest request) {
        send(NOTIFICATION_SETTINGS_SEND_PATH, request);
    }

    public void sendNotification(SendNotificationRequest request) {
        send(NOTIFICATION_SEND_PATH, request);
    }
}