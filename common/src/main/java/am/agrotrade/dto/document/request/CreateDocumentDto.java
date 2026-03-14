package am.agrotrade.dto.document.request;

import am.agrotrade.model.enums.DocumentFormat;
import am.agrotrade.model.enums.DocumentType;

import java.time.LocalDateTime;

public record CreateDocumentDto(

        DocumentType type,
        String name,
        DocumentFormat format,
        String filePath,
        LocalDateTime createdAt
) {
}
