package dev.hspl.taskbazi.task.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.usage.write.cmd.CloseProjectCommand;
import org.springframework.lang.NonNull;

public interface CloseProjectUseCase {
    void execute(
            @NonNull UniversalUser authenticatedUser,
            @NonNull CloseProjectCommand command
    );
}
