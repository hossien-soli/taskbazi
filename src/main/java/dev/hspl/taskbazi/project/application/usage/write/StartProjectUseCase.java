package dev.hspl.taskbazi.project.application.usage;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.cmd.StartProjectCommand;

public interface StartProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            StartProjectCommand command
    );
}
