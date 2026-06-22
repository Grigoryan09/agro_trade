package am.agrotrade.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettingsEvent {

    @NotNull(message = "UserId is required")
    @Positive(message = "UserId must be positive")
    private Long userId;

    @NotNull(message = "Email is required")
    @Email
    private String email;

    @NotNull(message = "Email setting is required")
    private Boolean emailEnabled;

    @NotNull(message = "SMS setting is required")
    private Boolean smsEnabled;

    @NotNull(message = "In-app setting is required")
    private Boolean inAppEnabled;
}