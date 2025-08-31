package kz.bailanysta.main.exception;

public class ForbiddenException extends IllegalArgumentException {

    private final String messageKey;
    private final Object[] args;

    public ForbiddenException() {
        this.messageKey = "Forbidden";
        this.args = new String[0];
    }

    public ForbiddenException(String messageKey, Object... args) {
        this.messageKey = messageKey;
        this.args = args;
    }

    public String getMessageKey() { return messageKey; }
    public Object[] getArgs() { return args; }

}
