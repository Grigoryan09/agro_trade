package am.agrotrade.common.dto;

import am.agrotrade.common.enums.ChatStatus;
import am.agrotrade.common.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDetailDto {

    private Long id;
    private String status;
    private String message;
    private LocalDateTime timestamp;

    private ChatType chatType;
    private ChatStatus chatStatus;
    private LocalDateTime lastActivity;
    private List<ChatMemberDto> members;
    private List<ChatMessageDto> messages;

}