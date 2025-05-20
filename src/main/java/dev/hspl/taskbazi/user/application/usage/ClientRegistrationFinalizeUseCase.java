package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationFinalizeCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationFinalizeResult;

public interface ClientRegistrationFinalizeUseCase {
    ClientRegistrationFinalizeResult execute(ClientRegistrationFinalizeCommand command);
}
