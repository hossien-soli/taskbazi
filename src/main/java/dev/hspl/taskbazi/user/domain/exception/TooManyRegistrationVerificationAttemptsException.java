package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;
import lombok.Getter;

@Getter
public class TooManyRegistrationVerificationAttemptsException extends DomainException {
    private final int maxAllowedAttempts;

    public TooManyRegistrationVerificationAttemptsException(int maxAllowedAttempts) {
        super("");
        this.maxAllowedAttempts = maxAllowedAttempts;
    }

    @Override
    public String problemKey() {
        return "user.registration_verification.too_many_attempts";
    }

    @Override
    public short groupingValue() {
        return 429;
    }
}
