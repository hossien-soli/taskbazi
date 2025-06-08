package dev.hspl.taskbazi.project.application.usage.write.cmd;

import dev.hspl.taskbazi.common.application.exception.InvalidApplicationCommandException;
import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.TaskPriority;
import dev.hspl.taskbazi.project.domain.value.TaskTitle;

import java.time.LocalDateTime;

public record TaskAssignmentCommand(
        ProjectId targetProjectId,
        UserId targetUserId,
        TaskTitle taskTitle,
        Description taskDescription, // optional-nullable
        TaskPriority taskPriority,
        LocalDateTime dueDateTime // optional-nullable
) {
    public TaskAssignmentCommand {
        boolean validate = targetProjectId != null && targetUserId != null && taskTitle != null && taskPriority != null;
        if (!validate) {
            throw new InvalidApplicationCommandException("task assignment command is invalid!");
        }
    }
}
