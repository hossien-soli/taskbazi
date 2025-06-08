package dev.hspl.taskbazi.common.domain.exception;

public abstract class DomainException extends RuntimeException implements ProblemAware {
    public DomainException(String exceptionMessage) { // don't send this message to users
        super(exceptionMessage);
    }
}
