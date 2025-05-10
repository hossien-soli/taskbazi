package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableVerificationCodeException extends DomainException {
    public UnacceptableVerificationCodeException() {
        super("Generated verification code is unacceptable. It must be exactly 10 characters in length.");
    }

    @Override
    public String problemKey() {
        return "user.verification_code.unacceptable";
    }
}
