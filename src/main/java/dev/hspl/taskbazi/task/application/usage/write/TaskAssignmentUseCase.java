package dev.hspl.taskbazi.task.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.usage.write.cmd.TaskAssignmentCommand;

public interface TaskAssignmentUseCase {
    void execute(UniversalUser authenticatedUser, TaskAssignmentCommand command);
}
