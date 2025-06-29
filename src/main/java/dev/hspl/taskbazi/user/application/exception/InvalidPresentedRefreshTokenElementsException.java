package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class InvalidPresentedRefreshTokenElementsException extends ApplicationException {
    public InvalidPresentedRefreshTokenElementsException() {
        super("elements for presented refresh token are invalid!");
    }

    @Override
    public String problemKey() {
        return "missing_required_data";
    }

    @Override
    public short groupingValue() {
        return 401;
    }
}
