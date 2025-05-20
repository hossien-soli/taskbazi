package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationRequestCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationRequestResult;

public interface ClientRegistrationRequestUseCase {
    ClientRegistrationRequestResult execute(ClientRegistrationRequestCommand command);
}
