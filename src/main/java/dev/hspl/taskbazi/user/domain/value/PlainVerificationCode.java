package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.UnacceptableVerificationCodeException;

public record PlainVerificationCode(String value) {
    public static final int LENGTH = 10;

    public PlainVerificationCode {
        boolean validate = value != null && value.length() == LENGTH;
        if (!validate) {
            throw new UnacceptableVerificationCodeException();
        }
    }
}
