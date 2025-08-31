package kz.bailanysta.main.exception;

public class InputException extends RuntimeException {

    public InputException(String message, Object... args) {
        super(message);
    }

    public InputException(String message) {
        super(message);
    }
}
