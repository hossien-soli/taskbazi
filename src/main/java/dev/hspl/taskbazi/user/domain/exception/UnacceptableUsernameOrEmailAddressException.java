package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;

public class UnacceptableUsernameOrEmailAddressException extends DomainException {
    public UnacceptableUsernameOrEmailAddressException() {
        super("The username or email address is invalid. Please provide at least a valid username or a valid email address.");
    }

    @Override
    public String problemKey() {
        return "user.username_or_email_address.unacceptable";
    }
}
