package dev.hspl.taskbazi.task.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class ProjectIsNotEditableException extends DomainException {
    public ProjectIsNotEditableException() {
        super("The project has been permanently closed and cannot be edited.");
    }

    @Override
    public String problemKey() {
        return "project.info.not_editable";
    }
}
