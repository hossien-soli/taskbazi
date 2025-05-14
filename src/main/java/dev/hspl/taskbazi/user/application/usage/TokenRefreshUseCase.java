package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.TokenRefreshCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRefreshResult;

public interface TokenRefreshUseCase {
    TokenRefreshResult execute(TokenRefreshCommand command);
}
