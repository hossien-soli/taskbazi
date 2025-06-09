package dev.hspl.taskbazi.task.domain.value;

import dev.hspl.taskbazi.task.domain.exception.UnacceptableTaskTitleException;

public record TaskTitle(String value) {
    public TaskTitle {
        boolean validate = value != null && value.length() >= 2 && value.length() <= 45;
        if (!validate) {
            throw new UnacceptableTaskTitleException();
        }
    }
}
