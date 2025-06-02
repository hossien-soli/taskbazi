package dev.hspl.taskbazi.project.application.exception;

import dev.hspl.taskbazi.common.application.ApplicationException;

// http status code = 404

public class InvalidProjectIdException extends ApplicationException {
    public InvalidProjectIdException() {
        super("no project found in system with provided id!");
    }

    @Override
    public String problemKey() {
        return "project.id.invalid";
    }
}
