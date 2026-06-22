package am.agrotrade.common.dto;

import am.agrotrade.common.enums.EmailType;

public record EmailNotificationEvent(
        String to,
        String code,
        String url,
        String productName,
        EmailType type
) {
}
