package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

// 403 http code

public class UserRoleMismatchLoginException extends DomainException {
    public UserRoleMismatchLoginException() {
        super("requested user role doesn't match the actual role of user!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }

    @Override
    public short groupingValue() {
        return 403;
    }
}
