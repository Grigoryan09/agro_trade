package am.agrotrade.core.client.impl;

import am.agrotrade.common.dto.request.OrderNotificationRequest;
import am.agrotrade.common.dto.request.SendNotificationSettingsRequest;
import am.agrotrade.common.dto.request.VerifyNotificationRequest;
import am.agrotrade.common.dto.request.WelcomeNotificationRequest;
import am.agrotrade.core.client.NotificationClient;
import am.agrotrade.core.exception.NotificationNonRetryableException;
import am.agrotrade.core.exception.NotificationRetryableException;
import am.agrotrade.core.properties.NotificationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationClientImpl implements NotificationClient {

    private final RestTemplate restTemplate;
    private final NotificationProperties properties;

    @Retryable(
            retryFor = {
                    NotificationRetryableException.class
            },
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2
            )
    )
    public void send(String path, Object request) {
        String url = UriComponentsBuilder
                .fromUriString(properties.url())
                .path(path)
                .toUriString();

        try {
            restTemplate.postForObject(url, request, Void.class);

        } catch (HttpStatusCodeException ex) {
            var status = ex.getStatusCode();
            var body = ex.getResponseBodyAsString();

            if (status.is5xxServerError()) {
                throw new NotificationRetryableException(
                        "Notification service 5xx error: status=%s, body=%s"
                                .formatted(status, body),
                        ex
                );
            }

            if (status.is4xxClientError()) {
                log.warn(
                        "Notification service returned 4xx. Request will NOT be retried. status={}, body={}",
                        status,
                        body
                );
                return;
            }

            throw new NotificationRetryableException(
                    "Unexpected HTTP status: status=%s, body=%s"
                            .formatted(status, body),
                    ex
            );
        } catch (ResourceAccessException ex) {
            throw new NotificationRetryableException(
                    "Notification service is unavailable",
                    ex
            );
        } catch (Exception ex) {
            throw new NotificationNonRetryableException(
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

    @Recover
    public void recover(NotificationRetryableException ex, String path, Object request) {
        log.error(
                "Notification request failed after retries. path={}, error={}",
                path,
                ex.getMessage(),
                ex
        );
    }
}
