package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.ApplicationException;

public class InvalidClientRegistrationRequestCommandException extends ApplicationException {
    public InvalidClientRegistrationRequestCommandException() {
        super("Provided client registration request command is invalid!");
    }

    @Override
    public String problemKey() {
        return "missing_required_data";
    }
}
