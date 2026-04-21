package am.agrotrade.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.chat-service")
public record ChatServiceProperties(
        String url
) {}