package dev.hspl.taskbazi.common.domain;

import dev.hspl.taskbazi.common.ProblemAware;

public abstract class DomainException extends RuntimeException implements ProblemAware {
    public DomainException(String exceptionMessage) { // don't send this message to users
        super(exceptionMessage);
    }
}
