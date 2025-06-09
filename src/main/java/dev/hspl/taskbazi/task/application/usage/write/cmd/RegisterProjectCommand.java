package dev.hspl.taskbazi.task.application.usage.write.cmd;

import dev.hspl.taskbazi.common.application.exception.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.task.domain.value.ProjectTitle;

public record RegisterProjectCommand(
        ProjectTitle projectTitle,
        Description projectDescription  // nullable
) {
    public RegisterProjectCommand {
        boolean validate = projectTitle != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("project registration command is invalid!");
        }
    }
}
