package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

// http status code: 400

public class InvalidTargetUserTaskAssignmentException extends DomainException {
    public InvalidTargetUserTaskAssignmentException() {
        super("target user should be owner or at least one ACTIVE collaborator");
    }

    @Override
    public String problemKey() {
        return "task.assignment.target_user.invalid";
    }
}
