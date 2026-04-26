package am.agrotrade.core.exception;

public class NotificationRetryableException extends RuntimeException {

    public NotificationRetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}