package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationFinalizeCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationFinalizeResult;
import org.springframework.lang.NonNull;

public interface ClientRegistrationFinalizeUseCase {
    ClientRegistrationFinalizeResult execute(
            ClientRegistrationFinalizeCommand command,
            @NonNull RequestClientIdentifier requestClientIdentifier
    );
}
