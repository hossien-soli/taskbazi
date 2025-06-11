package dev.hspl.taskbazi.task.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class UnacceptableProjectStatusTaskAssignmentException extends DomainException {
    public UnacceptableProjectStatusTaskAssignmentException() {
        super("the required project status for task assignment is: IN_PROGRESS");
    }

    @Override
    public String problemKey() {
        return "task.assignment.project_status.unacceptable";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
