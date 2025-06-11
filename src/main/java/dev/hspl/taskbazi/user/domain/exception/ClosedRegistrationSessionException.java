package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class ClosedRegistrationSessionException extends DomainException {
    public ClosedRegistrationSessionException() {
        super("The registration session has already been closed.");
    }

    @Override
    public String problemKey() {
        return "user.registration_session.closed";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
