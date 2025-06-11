package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class MissingProtectedPasswordException extends DomainException {
    public MissingProtectedPasswordException() {
        super("Protected password not provided.");
    }

    @Override
    public String problemKey() {
        return "user.protected_password.missing";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
