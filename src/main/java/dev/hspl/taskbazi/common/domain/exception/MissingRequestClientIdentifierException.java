package dev.hspl.taskbazi.common.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class MissingRequestClientIdentifierException extends DomainException {
    public MissingRequestClientIdentifierException() {
        super("Request client identifier not provided.");
    }

    @Override
    public String problemKey() {
        return "common.client_identifier.missing";
    }
}
