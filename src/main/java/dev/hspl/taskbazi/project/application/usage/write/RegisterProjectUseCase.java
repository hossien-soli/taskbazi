package dev.hspl.taskbazi.project.application.usage;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.cmd.RegisterProjectCommand;

public interface RegisterProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            RegisterProjectCommand command
    );
}
