package am.agrotrade.common.dto.user.request;

public record UpdateUserPasswordRequest(

        String oldPassword,
        String newPassword
) {
}
