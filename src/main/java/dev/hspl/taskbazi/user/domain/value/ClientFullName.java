package dev.hspl.taskbazi.user.domain.value;

import dev.hspl.taskbazi.user.domain.exception.UnacceptableClientFullNameException;

public record ClientFullName(String value) {
    public ClientFullName {
        boolean validate = value != null && value.length() >= 5 && value.length() <= 40;
        if (!validate) {
            throw new UnacceptableClientFullNameException();
        }
    }
}
