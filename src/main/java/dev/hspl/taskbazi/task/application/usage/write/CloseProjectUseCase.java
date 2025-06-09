package dev.hspl.taskbazi.task.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.usage.write.cmd.CloseProjectCommand;

public interface CloseProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            CloseProjectCommand command
    );
}
