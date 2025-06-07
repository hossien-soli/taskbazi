package dev.hspl.taskbazi.project.application.usage.write.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;

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
