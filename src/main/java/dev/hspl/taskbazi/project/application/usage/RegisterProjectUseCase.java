package dev.hspl.taskbazi.project.application.usage;

import dev.hspl.taskbazi.project.application.usage.cmd.RegisterProjectCommand;
import dev.hspl.taskbazi.project.application.usage.result.RegisterProjectResult;

public interface RegisterProjectUseCase {
    RegisterProjectResult execute(RegisterProjectCommand command);
}
