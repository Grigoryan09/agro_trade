package am.agrotrade.common.dto.response;

import am.agrotrade.common.enums.ErrorCode;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String message,
        ErrorCode errorCode,
        Instant timestamp
) {}