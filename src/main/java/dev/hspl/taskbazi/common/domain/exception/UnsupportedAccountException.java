package dev.hspl.taskbazi.common.domain.exception;

// http status code: 403

public class UnsupportedAccountException extends DomainException {
    public UnsupportedAccountException() {
        super("the account must have a 'CLIENT' role and be active to perform this action!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
