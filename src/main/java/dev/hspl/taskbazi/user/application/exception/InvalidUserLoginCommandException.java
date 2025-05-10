package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.ApplicationException;

public class InvalidUserLoginCommandException extends ApplicationException {
    public InvalidUserLoginCommandException() {
        super("Provided user login command is invalid!");
    }

    @Override
    public String problemKey() {
        return "missing_required_data";
    }
}
