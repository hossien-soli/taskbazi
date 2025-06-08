package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class ActualRefreshTokenMismatchException extends DomainException {
    public ActualRefreshTokenMismatchException() {
        super("user's presented refresh token doesn't match the stored token");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
