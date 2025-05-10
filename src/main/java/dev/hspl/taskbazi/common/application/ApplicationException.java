package dev.hspl.taskbazi.common.application;

import dev.hspl.taskbazi.common.ProblemAware;

public abstract class ApplicationException extends RuntimeException implements ProblemAware {
    public ApplicationException(String exceptionMessage) { // don't send this message to users
        super(exceptionMessage);
    }
}
