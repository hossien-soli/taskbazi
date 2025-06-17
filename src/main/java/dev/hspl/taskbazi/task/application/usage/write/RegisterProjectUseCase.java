package dev.hspl.taskbazi.task.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.usage.write.cmd.RegisterProjectCommand;
import org.springframework.lang.NonNull;

public interface RegisterProjectUseCase {
    void execute(
            @NonNull UniversalUser authenticatedUser,
            @NonNull RegisterProjectCommand command
    );
}
