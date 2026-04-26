package am.agrotrade.core.exception;

public class NotificationNonRetryableException extends RuntimeException {

    public NotificationNonRetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}