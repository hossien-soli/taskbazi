package dev.hspl.taskbazi.user.application.usage.result;

import dev.hspl.taskbazi.user.domain.value.RegistrationVerificationResult;

public record ClientRegistrationFinalizeResult(
        boolean userRegistered,
        RegistrationVerificationResult verificationResult
) {
}
