package am.agrotrade.core.exception;

public class ChatCreationException extends RuntimeException {
    public ChatCreationException(String message,Throwable cause) {
        super(message,cause);
    }
    public ChatCreationException(String message) {
        super(message);
    }
}