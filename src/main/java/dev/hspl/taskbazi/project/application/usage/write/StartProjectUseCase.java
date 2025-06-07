package dev.hspl.taskbazi.project.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.write.cmd.StartProjectCommand;

public interface StartProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            StartProjectCommand command
    );
}
