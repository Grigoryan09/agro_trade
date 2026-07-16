package am.agrotrade.common.dto.document;

public record ChatStatusUpdateEvent(
        long chatId,
        String status
) {
}
