package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableProjectDescriptionException extends DomainException {
    public UnacceptableProjectDescriptionException() {
        super("The project description is unacceptable. It must be between 10 and 500 characters long.");
    }

    @Override
    public String problemKey() {
        return "project.description.unacceptable";
    }
}
