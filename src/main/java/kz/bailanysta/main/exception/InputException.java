package kz.bailanysta.main.exception;

import lombok.Getter;

@Getter
public class InputException extends RuntimeException {
    private final String messageKey;
    private final Object[] args;

    public InputException(String messageKey, Object... args) {
        this.messageKey = messageKey;
        this.args = args;
    }
}
