package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class MissingProjectIdException extends DomainException {
    public MissingProjectIdException() {
        super("project id is missing!!!");
    }

    @Override
    public String problemKey() {
        return "project.project_id.missing";
    }
}
