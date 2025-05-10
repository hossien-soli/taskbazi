package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class InvalidAccessTokenException extends DomainException {
    public InvalidAccessTokenException() {
        super("The access token is invalid.");
    }

    @Override
    public String problemKey() {
        return "user.access_token.invalid";
    }
}
