package dev.hspl.taskbazi.common.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableAccountUsernameException extends DomainException {
    public UnacceptableAccountUsernameException() {
        super("Username is unacceptable. It must be between 5 and 30 characters long and can only contain English letters, numbers, underscores, and hyphens.");
    }

    @Override
    public String problemKey() {
        return "common.username.unacceptable";
    }
}
