package am.agrotrade.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.notification-service")
public record NotificationProperties(
        String url,
        String sendSettingsPath,
        String sendVerifyPath,
        String sendOrderOpenedPath,
        String sendWelcomePath
) {}
