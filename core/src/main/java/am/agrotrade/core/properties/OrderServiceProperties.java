package am.agrotrade.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order.service")
public record OrderServiceProperties(
        String baseUrl,
        String orderPath
) {}