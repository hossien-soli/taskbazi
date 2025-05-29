package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnsupportedAccountProjectRegistrationException extends DomainException {
    public UnsupportedAccountProjectRegistrationException() {
        super("the account must have a 'CLIENT' role and be active to register a project!");
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
