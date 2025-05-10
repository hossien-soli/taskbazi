package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.MissingOpaqueTokenException;

public record PlainOpaqueToken(String value) {
    public PlainOpaqueToken {
        boolean validate = value != null && !value.isEmpty();
        if (!validate) {
            throw new MissingOpaqueTokenException();
        }
    }
}
