package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class SelfTaskAssignmentNotAllowedException extends DomainException {
    public SelfTaskAssignmentNotAllowedException() {
        super("self task assignment is not allowed for the assigner user!");
    }

    @Override
    public String problemKey() {
        return "task.self_assignment.not_allowed";
    }
}
