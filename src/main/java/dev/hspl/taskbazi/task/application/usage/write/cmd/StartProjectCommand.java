package dev.hspl.taskbazi.task.application.usage.write.cmd;

import dev.hspl.taskbazi.common.application.exception.InvalidApplicationCommandException;
import dev.hspl.taskbazi.task.domain.value.ProjectId;

public record StartProjectCommand(
        ProjectId projectId,
        Integer clientEntityVersion  // nullable(this version only checks when provided by clients using If-Match header)
) {
    public StartProjectCommand {
        boolean validate = projectId != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("start project command is invalid!");
        }
    }

    public boolean isEntityVersionProvided() {
        return clientEntityVersion != null && clientEntityVersion != 0;
    }
}
