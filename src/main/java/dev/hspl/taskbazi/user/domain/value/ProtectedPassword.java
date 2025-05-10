package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.MissingProtectedPasswordException;

// value = hashed password
public record ProtectedPassword(String value) {
    public ProtectedPassword {
        boolean validate = value != null && !value.isEmpty();
        if (!validate) {
            throw new MissingProtectedPasswordException();
        }
    }
}
