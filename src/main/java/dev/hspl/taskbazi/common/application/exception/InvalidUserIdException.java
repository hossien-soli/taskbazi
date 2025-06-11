package dev.hspl.taskbazi.common.application.exception;

public class InvalidUserIdException extends ApplicationException {
    public InvalidUserIdException() {
        super("no user found with provided id!");
    }

    @Override
    public String problemKey() {
        return "common.user_id.invalid";
    }

    @Override
    public short groupingValue() {
        return 404;
    }
}
