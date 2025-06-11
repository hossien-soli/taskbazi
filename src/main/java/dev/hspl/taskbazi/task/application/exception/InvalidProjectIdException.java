package dev.hspl.taskbazi.task.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class InvalidProjectIdException extends ApplicationException {
    public InvalidProjectIdException() {
        super("no project found with provided id!");
    }

    @Override
    public String problemKey() {
        return "project.id.invalid";
    }

    @Override
    public short groupingValue() {
        return 404;
    }
}
