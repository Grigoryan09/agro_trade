package am.agrotrade.dto.user.response;

public record AuthResponse(

        String token,
        UserResponse userResponse
) {
}
