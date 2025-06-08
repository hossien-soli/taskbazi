package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class MissingAccessTokenException extends DomainException {
    public MissingAccessTokenException() {
        super("The access token was not found!");
    }

    @Override
    public String problemKey() {
        return "user.access_token.missing";
    }
}
