package dev.hspl.taskbazi.project.domain.value;

import dev.hspl.taskbazi.project.domain.exception.MissingProjectIdException;

import java.util.UUID;

public record ProjectId(UUID value) {
    public ProjectId {
        boolean validate = value != null;
        if (!validate) {
            throw new MissingProjectIdException();
        }
    }
}
