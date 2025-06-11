package dev.hspl.taskbazi.task.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class InvalidTargetUserTaskAssignmentException extends DomainException {
    public InvalidTargetUserTaskAssignmentException() {
        super("target user should be owner or at least one ACTIVE collaborator");
    }

    @Override
    public String problemKey() {
        return "task.assignment.target_user.invalid";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
