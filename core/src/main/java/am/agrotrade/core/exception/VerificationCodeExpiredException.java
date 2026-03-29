package am.agrotrade.core.exception;

public class VerificationCodeExpiredException extends RuntimeException {

    public VerificationCodeExpiredException(String message) {
        super(message);
    }

}