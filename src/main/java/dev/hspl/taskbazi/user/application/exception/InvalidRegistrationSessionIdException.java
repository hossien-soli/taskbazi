package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

// http status code = 404

public class InvalidRegistrationSessionIdException extends ApplicationException {
    public InvalidRegistrationSessionIdException() {
        super("The ID of client registration session is invalid!");
    }

    @Override
    public String problemKey() {
        return "user.registration_session.invalid_id";
    }
}
