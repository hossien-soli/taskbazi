package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.MissingAccessTokenException;

public record AccessToken(String value) {
    public AccessToken {
        boolean validate = value != null && !value.isEmpty();
        if (!validate) {
            throw new MissingAccessTokenException();
        }
    }
}
