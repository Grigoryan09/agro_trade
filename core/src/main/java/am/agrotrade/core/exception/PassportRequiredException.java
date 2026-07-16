package am.agrotrade.core.exception;

public class PassportRequiredException extends RuntimeException {
    public PassportRequiredException(String message) {
        super(message);
    }
}
