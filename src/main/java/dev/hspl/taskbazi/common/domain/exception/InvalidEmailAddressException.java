package dev.hspl.taskbazi.common.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class InvalidEmailAddressException extends DomainException {
    public InvalidEmailAddressException() {
        super("Email address is invalid.");
    }

    @Override
    public String problemKey() {
        return "common.email_address.invalid";
    }
}
