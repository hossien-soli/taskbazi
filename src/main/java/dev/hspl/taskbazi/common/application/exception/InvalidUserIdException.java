package dev.hspl.taskbazi.common.application.exception;

// http status code = 404

public class InvalidUserIdException extends ApplicationException {
    public InvalidUserIdException() {
        super("no user found with provided id!");
    }

    @Override
    public String problemKey() {
        return "common.user_id.invalid";
    }
}
