package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class PasswordMismatchException extends DomainException {
    public PasswordMismatchException() {
        super("The provided password does not match our records.");
    }

    @Override
    public String problemKey() {
        return "user.password.mismatch";
    }
}
