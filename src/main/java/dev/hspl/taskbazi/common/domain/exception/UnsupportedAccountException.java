package dev.hspl.taskbazi.common.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnsupportedAccountException extends DomainException {
    public UnsupportedAccountException() {
        super("the account must have a 'CLIENT' role and be active to perform this action!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
