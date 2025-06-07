package dev.hspl.taskbazi.project.application.usage;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.project.application.usage.cmd.TaskAssignmentCommand;

public interface TaskAssignmentUseCase {
    void execute(UniversalUser authenticatedUser, TaskAssignmentCommand command);
}
