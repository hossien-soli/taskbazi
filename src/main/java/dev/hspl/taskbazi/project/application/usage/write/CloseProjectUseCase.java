package dev.hspl.taskbazi.project.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.write.cmd.CloseProjectCommand;

public interface CloseProjectUseCase {
    void execute(
            UniversalUser authenticatedUser,
            CloseProjectCommand command
    );
}
