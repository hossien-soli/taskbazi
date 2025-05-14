package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;

import java.util.UUID;

public record ClientRegistrationFinalizeCommand(
        UUID registrationSessionId,
        PlainVerificationCode verificationCode
) {
    public ClientRegistrationFinalizeCommand {
        boolean validate = registrationSessionId != null && verificationCode != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("Provided client registration finalize command is invalid!");
        }
    }
}
