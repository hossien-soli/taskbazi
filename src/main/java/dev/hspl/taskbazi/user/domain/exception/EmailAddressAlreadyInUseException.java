package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class EmailAddressAlreadyInUseException extends DomainException {
    public EmailAddressAlreadyInUseException() {
        super("The email address is already in use.");
    }

    @Override
    public String problemKey() {
        return "user.email_address.in_use";
    }
}
