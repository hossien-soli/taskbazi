package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.UserLoginCommand;
import dev.hspl.taskbazi.user.application.usage.result.UserLoginResult;

public interface UserLoginUseCase {
    UserLoginResult execute(UserLoginCommand command);
}
