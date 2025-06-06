package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableProjectStatusTaskAssignmentException extends DomainException {
    public UnacceptableProjectStatusTaskAssignmentException() {
        super("the required project status for task assignment is: IN_PROGRESS");
    }

    @Override
    public String problemKey() {
        return "task.assignment.project_status.unacceptable";
    }
}
