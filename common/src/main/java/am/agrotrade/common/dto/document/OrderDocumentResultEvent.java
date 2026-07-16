package am.agrotrade.common.dto.document;

public record OrderDocumentResultEvent(
        long chatId,
        long senderUserId,
        String fileName,
        String base64Document
) {
}
