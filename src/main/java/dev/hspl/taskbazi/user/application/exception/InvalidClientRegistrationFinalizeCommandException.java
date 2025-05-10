package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.ApplicationException;

public class InvalidClientRegistrationFinalizeCommandException extends ApplicationException {
    public InvalidClientRegistrationFinalizeCommandException() {
        super("Provided client registration finalize command is invalid!");
    }

    @Override
    public String problemKey() {
        return "missing_required_data";
    }
}
