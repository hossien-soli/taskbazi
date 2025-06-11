package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class InvalidRegistrationSessionIdException extends ApplicationException {
    public InvalidRegistrationSessionIdException() {
        super("The ID of client registration session is invalid!");
    }

    @Override
    public String problemKey() {
        return "user.registration_session.invalid_id";
    }

    @Override
    public short groupingValue() {
        return 404;
    }
}
