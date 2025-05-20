package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.UserFullName;

// all fields are required!

public record ClientRegistrationRequestCommand(
        UserFullName fullName,
        EmailAddress emailAddress,
        Username username,
        PlainPassword password,
        PlainPassword passwordConfirmation,
        RequestClientIdentifier requestClientIdentifier
) {
    public ClientRegistrationRequestCommand {
        boolean validate = fullName != null && emailAddress != null && username != null
                && password != null && passwordConfirmation != null && requestClientIdentifier != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("Provided client registration request command is invalid!");
        }
    }
}
