package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class UsernameAlreadyInUseException extends DomainException {
    public UsernameAlreadyInUseException() {
        super("The username is already in use.");
    }

    @Override
    public String problemKey() {
        return "user.username.in_use";
    }
}
