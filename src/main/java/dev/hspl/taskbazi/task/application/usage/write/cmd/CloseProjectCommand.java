package dev.hspl.taskbazi.task.application.usage.write.cmd;

import dev.hspl.taskbazi.common.application.exception.InvalidApplicationCommandException;
import dev.hspl.taskbazi.task.domain.value.ProjectCloseStatus;
import dev.hspl.taskbazi.task.domain.value.ProjectId;

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
