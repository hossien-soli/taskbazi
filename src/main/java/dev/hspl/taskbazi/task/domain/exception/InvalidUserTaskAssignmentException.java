package dev.hspl.taskbazi.task.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

// http status code: 403

public class InvalidUserTaskAssignmentException extends DomainException {
    public InvalidUserTaskAssignmentException() {
        super("the assigner-user should be owner or at least one active collaborator with one task assignment permission(self/managing)");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }

    @Override
    public short groupingValue() {
        return 403;
    }
}
