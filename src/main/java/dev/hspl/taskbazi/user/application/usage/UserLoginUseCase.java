package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.UserLoginCommand;
import dev.hspl.taskbazi.user.application.usage.result.UserLoginResult;
import org.springframework.lang.NonNull;

public interface UserLoginUseCase {
    UserLoginResult execute(@NonNull UserLoginCommand command);
}
