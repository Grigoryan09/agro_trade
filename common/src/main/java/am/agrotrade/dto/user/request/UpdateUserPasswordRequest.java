package am.agrotrade.dto.user.request;

public record UpdateUserPasswordRequest(

        String oldPassword,
        String newPassword
) {
}
