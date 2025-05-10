package dev.hspl.taskbazi.user.application.usage.result;

import java.util.UUID;

public record ClientRegistrationRequestResult(
        UUID registrationSessionId,
        int sessionLifetimeSeconds,
        int sessionLimitationDelaySeconds,
        short maxAllowedAttempts
) {
}
