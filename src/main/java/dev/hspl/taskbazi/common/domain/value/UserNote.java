package dev.hspl.taskbazi.common.domain.value;

import dev.hspl.taskbazi.common.domain.exception.UnacceptableUserNoteException;

// also check for Cross-Site Scripting in infrastructure layer

public record UserNote(String value) {
    public UserNote {
        boolean validate = value != null && value.length() >= 5 && value.length() <= 300;
        if (!validate) {
            throw new UnacceptableUserNoteException();
        }
    }
}
