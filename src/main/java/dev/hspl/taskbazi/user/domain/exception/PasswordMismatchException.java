package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class PasswordMismatchException extends DomainException {
    public PasswordMismatchException() {
        super("The provided password does not match our records.");
    }

    @Override
    public String problemKey() {
        return "user.password.mismatch";
    }

    @Override
    public short groupingValue() {
        return 401;
    }
}
