package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class MissingPresentedRefreshTokenException extends ApplicationException {
    public MissingPresentedRefreshTokenException() {
        super("presented refresh token by user is missing!!!");
    }

    @Override
    public String problemKey() {
        return "missing_required_data";
    }
}
