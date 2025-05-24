package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

// TODO: add messages

public class ProjectIsNotEditableException extends DomainException {
    public ProjectIsNotEditableException() {
        super("");
    }

    @Override
    public String problemKey() {
        return "";
    }
}
