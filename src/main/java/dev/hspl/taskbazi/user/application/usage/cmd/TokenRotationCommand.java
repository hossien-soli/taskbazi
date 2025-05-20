package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;

public record TokenRotationCommand(
        PresentedRefreshToken presentedRefreshToken, // required
        RequestClientIdentifier requestClientIdentifier, // required
        RequestIdentificationDetails requestIdentificationDetails // optional
) {
    public TokenRotationCommand {
        boolean validate = presentedRefreshToken != null && requestClientIdentifier != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("Provided token rotation command is invalid!");
        }
    }
}
