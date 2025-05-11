package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.MissingUserIdException;

import java.util.UUID;

// a universal contract in the whole application for detecting the users

public record UserId(UUID value) {
    public UserId {
        boolean validate = value != null;
        if (!validate) {
            throw new MissingUserIdException();
        }
    }
}
