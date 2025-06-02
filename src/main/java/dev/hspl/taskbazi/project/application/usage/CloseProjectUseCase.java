package dev.hspl.taskbazi.project.application.usage;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.cmd.CloseProjectCommand;

public interface CloseProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            CloseProjectCommand command
    );
}
