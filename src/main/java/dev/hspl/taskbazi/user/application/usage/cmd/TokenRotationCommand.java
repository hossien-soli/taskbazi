package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;

public record TokenRotationCommand(PresentedRefreshToken presentedRefreshToken) {
    public TokenRotationCommand {
        boolean validate = presentedRefreshToken != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("Provided token rotation command is invalid!");
        }
    }
}
