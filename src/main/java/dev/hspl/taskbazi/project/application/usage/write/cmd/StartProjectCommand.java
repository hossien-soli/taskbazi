package dev.hspl.taskbazi.project.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.project.domain.value.ProjectId;

public record StartProjectCommand(
        ProjectId projectId,
        Integer clientResourceVersion
) {
    public StartProjectCommand {
        boolean validate = projectId != null && clientResourceVersion != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("start project command is invalid!");
        }
    }
}
