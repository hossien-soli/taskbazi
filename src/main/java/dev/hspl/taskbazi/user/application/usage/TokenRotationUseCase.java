package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.TokenRotationCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRotationResult;
import org.springframework.lang.NonNull;

public interface TokenRotationUseCase {
    TokenRotationResult execute(@NonNull TokenRotationCommand command);
}
