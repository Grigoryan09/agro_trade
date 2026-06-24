package am.agrotrade.core.exception;

/**
 * Exception thrown when a user operation is attempted without valid roles.
 */
public class InvalidUserRoleException extends RuntimeException {

    public InvalidUserRoleException(String message) {
        super(message);
    }
}