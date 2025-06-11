package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class InvalidUsernameOrEmailAddressLoginException extends DomainException {
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
