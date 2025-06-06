package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

// http status code: 400

public class UnsupportedTargetAccountTaskAssignmentException extends DomainException {
    public UnsupportedTargetAccountTaskAssignmentException() {
        super("the target account must have a 'CLIENT' role and be active for task assignment!");
    }

    @Override
    public String problemKey() {
        return "task.assignment.target_account.unsupported";
    }
}
