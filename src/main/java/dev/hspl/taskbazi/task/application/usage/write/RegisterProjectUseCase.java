package dev.hspl.taskbazi.task.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.usage.write.cmd.RegisterProjectCommand;

public interface RegisterProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            RegisterProjectCommand command
    );
}
