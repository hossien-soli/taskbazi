package dev.hspl.taskbazi.common.domain.exception;

public class RequestClientIdentifierMismatchException extends DomainException {
    public RequestClientIdentifierMismatchException() {
        super("Request client identifier registered in the system does not match the new identifier.");
    }

    @Override
    public String problemKey() {
        return "common.client_identifier.mismatch";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
