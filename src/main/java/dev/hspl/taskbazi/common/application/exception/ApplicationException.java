package dev.hspl.taskbazi.common.application.exception;

import dev.hspl.taskbazi.common.domain.exception.ProblemAware;

public abstract class ApplicationException extends RuntimeException implements ProblemAware {
    public ApplicationException(String exceptionMessage) { // don't send this message to users
        super(exceptionMessage);
    }
}
