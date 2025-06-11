package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class PasswordConfirmationMismatchException extends ApplicationException {
    public PasswordConfirmationMismatchException() {
        super("Actual password doesn't match it's confirmation!");
    }

    @Override
    public String problemKey() {
        return "user.password_confirmation.mismatch";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
