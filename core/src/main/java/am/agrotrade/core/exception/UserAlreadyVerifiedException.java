package am.agrotrade.core.exception;

public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException(String message) {
        super(message);
    }
}
