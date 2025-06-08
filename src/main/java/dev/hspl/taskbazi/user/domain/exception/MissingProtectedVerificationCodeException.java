package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class MissingProtectedVerificationCodeException extends DomainException {
    public MissingProtectedVerificationCodeException() {
        super("Protected verification code not provided.");
    }

    @Override
    public String problemKey() {
        return "user.protected_verification_code.missing";
    }
}
