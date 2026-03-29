package am.agrotrade.common.dto.document.request;

import am.agrotrade.common.enums.DocumentFormat;
import am.agrotrade.common.enums.DocumentType;

import java.time.LocalDateTime;

public record CreateDocumentDto(

        DocumentType type,
        String name,
        DocumentFormat format,
        String filePath,
        LocalDateTime createdAt
) {
}
