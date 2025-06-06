package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class ClosedLoginSessionException extends DomainException {
    public ClosedLoginSessionException() {
        super("The login session has expired, been invalidated, or ended due to user logout!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
