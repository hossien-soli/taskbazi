package dev.hspl.taskbazi.project.domain.value;

import dev.hspl.taskbazi.project.domain.exception.UnacceptableProjectDescriptionException;

public record ProjectDescription(String value) {
    public ProjectDescription {
        boolean validate = value != null && value.length() >= 10 && value.length() <= 500;
        if (!validate) {
            throw new UnacceptableProjectDescriptionException();
        }
    }
}
