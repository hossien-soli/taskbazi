package dev.hspl.taskbazi.user.application.usage.cmd;

import dev.hspl.taskbazi.user.application.exception.InvalidClientRegistrationRequestCommandException;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.common.domain.value.Username;

public record ClientRegistrationRequestCommand(
        ClientFullName fullName,
        EmailAddress emailAddress,
        Username username,
        PlainPassword password,
        PlainPassword passwordConfirmation
) {
    public ClientRegistrationRequestCommand {
        boolean validate = fullName != null && emailAddress != null && username != null
                && password != null && passwordConfirmation != null;
        if (!validate) {
            throw new InvalidClientRegistrationRequestCommandException();
        }
    }
}
