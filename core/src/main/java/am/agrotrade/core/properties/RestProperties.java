package am.agrotrade.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rest")
public record RestProperties(
        int connectTimeout,
        int readTimeout
) {}