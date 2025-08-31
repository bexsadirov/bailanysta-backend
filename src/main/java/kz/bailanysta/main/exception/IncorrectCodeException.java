package kz.bailanysta.main.exception;

/**
 * @author Beksultan
 */
public class IncorrectCodeException extends InputException {

    public IncorrectCodeException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
