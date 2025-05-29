package dev.hspl.taskbazi.project.domain.value;

import dev.hspl.taskbazi.project.domain.exception.UnacceptableTaskTitleException;

public record TaskTitle(String value) {
    public TaskTitle {
        boolean validate = value != null && value.length() >= 2 && value.length() <= 45;
        if (!validate) {
            throw new UnacceptableTaskTitleException();
        }
    }
}
