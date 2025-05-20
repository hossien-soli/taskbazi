package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;
import dev.hspl.taskbazi.user.domain.value.UsernameOrEmailAddress;

public record UserLoginCommand(
        UserRole roleToLogin, // required
        UsernameOrEmailAddress usernameOrEmailAddress, // required
        PlainPassword password, // required
        RequestClientIdentifier requestClientIdentifier, // required
        RequestIdentificationDetails requestIdentificationDetails // optional
) {
    public UserLoginCommand {
        boolean validate = roleToLogin != null && usernameOrEmailAddress != null && password != null
                && requestClientIdentifier != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("Provided user login command is invalid!");
        }
    }
}
