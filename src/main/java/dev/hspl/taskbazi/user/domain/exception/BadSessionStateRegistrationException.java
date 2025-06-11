package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class BadSessionStateRegistrationException extends DomainException {
    public BadSessionStateRegistrationException() {
        super("Registration cannot be done for the selected session.");
    }

    @Override
    public String problemKey() {
        return "user.registration.bad_session_state";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
