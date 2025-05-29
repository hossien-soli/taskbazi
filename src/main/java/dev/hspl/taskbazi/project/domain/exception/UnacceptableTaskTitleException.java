package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableTaskTitleException extends DomainException {
    public UnacceptableTaskTitleException() {
        super("The task title is unacceptable. It must be between 2 and 45 characters long.");
    }

    @Override
    public String problemKey() {
        return "task.title.unacceptable";
    }
}
