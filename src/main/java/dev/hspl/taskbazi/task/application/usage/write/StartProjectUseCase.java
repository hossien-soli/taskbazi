package dev.hspl.taskbazi.task.application.usage.write;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.usage.write.cmd.StartProjectCommand;
import org.springframework.lang.NonNull;

public interface StartProjectUseCase {
    void execute(
            @NonNull UniversalUser authenticatedUser,
            @NonNull StartProjectCommand command
    );
}
