package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableProjectTitleException extends DomainException {
    public UnacceptableProjectTitleException() {
        super("The project title is unacceptable. It must be between 5 and 50 characters long.");
    }

    @Override
    public String problemKey() {
        return "project.title.unacceptable";
    }
}
