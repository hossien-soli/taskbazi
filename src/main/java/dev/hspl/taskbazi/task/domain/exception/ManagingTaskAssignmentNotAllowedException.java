package dev.hspl.taskbazi.task.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class ManagingTaskAssignmentNotAllowedException extends DomainException {
    public ManagingTaskAssignmentNotAllowedException() {
        super("assigning task to other collaborators(managing) is not allowed for the assigner user");
    }

    @Override
    public String problemKey() {
        return "task.managing_assignment.not_allowed";
    }

    @Override
    public short groupingValue() {
        return 403;
    }
}
