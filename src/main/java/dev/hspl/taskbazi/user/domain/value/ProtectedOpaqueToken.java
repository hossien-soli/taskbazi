package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.MissingProtectedOpaqueTokenException;

public record ProtectedOpaqueToken(String value) {
    public ProtectedOpaqueToken {
        boolean validate = value != null && !value.isEmpty();
        if (!validate) {
            throw new MissingProtectedOpaqueTokenException();
        }
    }
}
