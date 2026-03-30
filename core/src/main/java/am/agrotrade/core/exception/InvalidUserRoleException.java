package am.agrotrade.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user operation is attempted without valid roles.
 */
public class InvalidUserRoleException extends RuntimeException {
    
    public InvalidUserRoleException(String message) {
        super(message);
    }
}