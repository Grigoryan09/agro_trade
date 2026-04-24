package am.agrotrade.core.client.impl;

import am.agrotrade.common.dto.request.OrderNotificationRequest;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;
import am.agrotrade.common.dto.request.VerifyNotificationRequest;
import am.agrotrade.common.dto.request.WelcomeNotificationRequest;
import am.agrotrade.core.client.NotificationClient;
import am.agrotrade.core.exception.NotificationException;
import am.agrotrade.core.properties.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NotificationClientImpl implements NotificationClient {

    private final RestTemplate restTemplate;
    private final NotificationProperties properties;

    private void send(String path, Object request) {
        String url = UriComponentsBuilder
                .fromUriString(properties.url())
                .path(path)
                .toUriString();

        try {
            restTemplate.postForObject(url, request, Void.class);

        } catch (HttpStatusCodeException ex) {
            throw new NotificationException(
                    "Notification service error: status=%s, body=%s"
                            .formatted(ex.getStatusCode(), ex.getResponseBodyAsString()),
                    ex
            );
        } catch (ResourceAccessException ex) {
            throw new NotificationException(
                    "Notification service is unavailable",
                    ex
            );
        } catch (Exception ex) {
            throw new NotificationException(
                    "Unexpected error while calling notification service: %s"
                            .formatted(ex.getMessage()),
                    ex
            );
        }
    }

    @Override
    public void sendNotificationSettings(SendNotificationSettingsRequest request) {
        send(properties.sendSettingsPath(), request);
    }

    @Override
    public void sendVerifyEmail(VerifyNotificationRequest request) {
        send(properties.sendVerifyPath(), request);
    }

    @Override
    public void sendOrderOpenedEmail(OrderNotificationRequest request) {
        send(properties.sendOrderOpenedPath(), request);
    }

    @Override
    public void sendWelcomeEmail(WelcomeNotificationRequest request) {
        send(properties.sendWelcomePath(), request);
    }
}
