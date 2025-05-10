package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.UnacceptablePlainPasswordException;

public record PlainPassword(String value) {
    public PlainPassword {
        boolean validate = value != null && value.length() >= 8 &&
                value.length() <= 70 && !value.contains(" ");
        if (!validate) {
            throw new UnacceptablePlainPasswordException();
        }
    }
}
