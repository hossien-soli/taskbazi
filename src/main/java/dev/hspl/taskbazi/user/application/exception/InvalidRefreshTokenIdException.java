package dev.hspl.taskbazi.user.application.exception;

import dev.hspl.taskbazi.common.application.exception.ApplicationException;

public class InvalidRefreshTokenIdException extends ApplicationException {
    public InvalidRefreshTokenIdException() {
        super("the id of refresh token is invalid! no record found with provided id!!!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }

    @Override
    public short groupingValue() {
        return 401;
    }
}
