package kz.bailanysta.main.exception;

/**
 * @author Beksultan
 */
public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
