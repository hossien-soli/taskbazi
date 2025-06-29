package dev.hspl.taskbazi.task.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class UnacceptableTaskTitleException extends DomainException {
    public UnacceptableTaskTitleException() {
        super("The task title is unacceptable. It must be between 2 and 45 characters long.");
    }

    @Override
    public String problemKey() {
        return "task.title.unacceptable";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
