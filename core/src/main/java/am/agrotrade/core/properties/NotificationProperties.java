package am.agrotrade.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.notification")
public record NotificationProperties(
        String url
) {}