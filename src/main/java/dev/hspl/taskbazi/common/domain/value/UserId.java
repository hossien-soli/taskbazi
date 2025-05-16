package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.MissingUserIdException;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        boolean validate = value != null;
        if (!validate) {
            throw new MissingUserIdException();
        }
    }
}
