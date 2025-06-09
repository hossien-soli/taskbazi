package dev.hspl.taskbazi.task.domain.value;

import dev.hspl.taskbazi.task.domain.exception.UnacceptableProjectTitleException;

public record ProjectTitle(String value) {
    public ProjectTitle {
        boolean validate = value != null && value.length() >= 5 && value.length() <= 50;
        if (!validate) {
            throw new UnacceptableProjectTitleException();
        }
    }
}
