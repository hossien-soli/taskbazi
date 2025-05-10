package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptablePlainPasswordException extends DomainException {
    public UnacceptablePlainPasswordException() {
        super("Plain password is unacceptable. It must be at least 8 characters long and must not contain any spaces.");
    }

    @Override
    public String problemKey() {
        return "user.plain_password.unacceptable";
    }
}
