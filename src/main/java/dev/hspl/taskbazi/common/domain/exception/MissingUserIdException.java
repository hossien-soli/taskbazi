package dev.hspl.taskbazi.common.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class MissingUserIdException extends DomainException {
    public MissingUserIdException() {
        super("user id is missing!!!");
    }

    @Override
    public String problemKey() {
        return "common.user_id.missing";
    }
}
