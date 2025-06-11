package dev.hspl.taskbazi.common.domain.exception;

public class InvalidEmailAddressException extends DomainException {
    public InvalidEmailAddressException() {
        super("Email address is invalid.");
    }

    @Override
    public String problemKey() {
        return "common.email_address.invalid";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
