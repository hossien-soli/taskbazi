package dev.hspl.taskbazi.project.application.usage.cmd;

import dev.hspl.taskbazi.common.application.InvalidApplicationCommandException;
import dev.hspl.taskbazi.project.domain.value.ProjectCloseStatus;
import dev.hspl.taskbazi.project.domain.value.ProjectId;

public record CloseProjectCommand(
        ProjectId projectId,
        ProjectCloseStatus closeStatus
) {
    public CloseProjectCommand {
        boolean validate = projectId != null && closeStatus != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("close project command is invalid!");
        }
    }
}
