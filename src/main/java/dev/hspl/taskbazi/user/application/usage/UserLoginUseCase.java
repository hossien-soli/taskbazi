package dev.hspl.taskbazi.user.application.usage;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;
import dev.hspl.taskbazi.user.application.usage.cmd.UserLoginCommand;
import dev.hspl.taskbazi.user.application.usage.result.UserLoginResult;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface UserLoginUseCase {
    UserLoginResult execute(
            UserLoginCommand command,
            boolean clientLogin,
            @NonNull RequestClientIdentifier requestClientIdentifier,
            @Nullable RequestIdentificationDetails requestIdentificationDetails
    );
}
