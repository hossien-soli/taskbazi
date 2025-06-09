package dev.hspl.taskbazi.task.application.usage.write.cmd;

import dev.hspl.taskbazi.common.application.exception.InvalidApplicationCommandException;
import dev.hspl.taskbazi.task.domain.value.ProjectId;

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
