package dev.hspl.taskbazi.common.domain.exception;

public class MissingRequestClientIdentifierException extends DomainException {
    public MissingRequestClientIdentifierException() {
        super("Request client identifier not provided.");
    }

    @Override
    public String problemKey() {
        return "common.client_identifier.missing";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
