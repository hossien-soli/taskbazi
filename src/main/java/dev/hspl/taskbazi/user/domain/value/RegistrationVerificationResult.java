package dev.hspl.taskbazi.user.domain.value;

public enum RegistrationVerificationResult {
    TOO_MANY_ATTEMPTS,
    EXPIRED,
    CODE_MISMATCH,
    SUCCESS
}
