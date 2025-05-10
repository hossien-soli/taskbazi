package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableClientFullNameException extends DomainException {
    public UnacceptableClientFullNameException() {
        super("The full name is unacceptable. It must be between 5 and 40 characters long.");
    }

    @Override
    public String problemKey() {
        return "user.full_name.unacceptable";
    }
}
