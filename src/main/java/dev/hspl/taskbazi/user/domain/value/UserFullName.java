package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.UnacceptableUserFullNameException;

public record UserFullName(String value) {
    public UserFullName {
        boolean validate = value != null && value.length() >= 5 && value.length() <= 40;
        if (!validate) {
            throw new UnacceptableUserFullNameException();
        }
    }
}
