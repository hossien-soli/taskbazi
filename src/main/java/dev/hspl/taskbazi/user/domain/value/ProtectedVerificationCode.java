package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.MissingProtectedVerificationCodeException;

public record ProtectedVerificationCode(String value) {
    public ProtectedVerificationCode {
        boolean validate = value != null && !value.isEmpty();
        if (!validate) {
            throw new MissingProtectedVerificationCodeException();
        }
    }
}
