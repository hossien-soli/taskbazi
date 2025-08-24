package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class InvalidUsernameOrEmailAddressLoginException extends ApplicationException {
    public InvalidUsernameOrEmailAddressLoginException() {
        super("The username or email address is invalid.");
    }

    @Override
    public String problemKey() {
        return "user.login.invalid_credentials";
    }

    @Override
    public short groupingValue() {
        return 401;
    }
}
