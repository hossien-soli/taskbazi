package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class ProjectIsNotStartableException extends DomainException {
    public ProjectIsNotStartableException() {
        super("The project has already started or has been permanently closed.");
    }

    @Override
    public String problemKey() {
        return "project.status.not_startable";
    }
}
