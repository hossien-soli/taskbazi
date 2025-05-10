package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationRequestCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationRequestResult;
import org.springframework.lang.NonNull;

public interface ClientRegistrationRequestUseCase {
    ClientRegistrationRequestResult execute(
            ClientRegistrationRequestCommand command,
            @NonNull RequestClientIdentifier requestClientIdentifier
    );
}
