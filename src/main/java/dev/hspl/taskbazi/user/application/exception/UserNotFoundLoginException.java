package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class UserNotFoundLoginException extends ApplicationException {
    public UserNotFoundLoginException() {
        super("the related user does not found!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }

    @Override
    public short groupingValue() {
        return 401;
    }
}
