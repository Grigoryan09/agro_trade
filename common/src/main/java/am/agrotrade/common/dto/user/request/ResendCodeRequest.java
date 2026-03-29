package am.agrotrade.common.dto.user.request;

import jakarta.validation.constraints.NotBlank;

public record ResendCodeRequest(@NotBlank String email) {}
