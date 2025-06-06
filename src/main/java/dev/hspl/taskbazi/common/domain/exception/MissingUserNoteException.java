package dev.hspl.taskbazi.common.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class MissingUserNoteException extends DomainException {
    public MissingUserNoteException() {
        super("The user note is unacceptable. It must be between 5 and 300 characters long.");
    }

    @Override
    public String problemKey() {
        return "common.user_note.unacceptable";
    }
}
