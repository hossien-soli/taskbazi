package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.TokenRotationCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRotationResult;

public interface TokenRotationUseCase {
    TokenRotationResult execute(TokenRotationCommand command);
}
