package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.UsernameOrEmailAddress;

public record UserLoginCommand(
        UserRole roleToLogin,
        UsernameOrEmailAddress usernameOrEmailAddress,
        PlainPassword password
) {
    public UserLoginCommand {
        boolean validate = roleToLogin != null && usernameOrEmailAddress != null && password != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("Provided user login command is invalid!");
        }
    }
}
