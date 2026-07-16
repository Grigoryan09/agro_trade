package am.agrotrade.common.dto.document;

public record ChatDocumentMessageEvent(
        long chatId,
        long senderUserId,
        String fileName,
        String fileUrl
) {
}
