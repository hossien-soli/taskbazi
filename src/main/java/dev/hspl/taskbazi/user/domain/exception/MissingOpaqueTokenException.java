package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class MissingOpaqueTokenException extends DomainException {
    public MissingOpaqueTokenException() {
        super("The token has not been provided.");
    }

    @Override
    public String problemKey() {
        return "user.actual_token.not_provided";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
