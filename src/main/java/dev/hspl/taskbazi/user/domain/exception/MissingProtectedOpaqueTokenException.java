package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class MissingProtectedOpaqueTokenException extends DomainException {
    public MissingProtectedOpaqueTokenException() {
        super("The protected token has not been provided.");
    }

    @Override
    public String problemKey() {
        return "user.protected_token.not_provided";
    }
}
