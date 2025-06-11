package dev.hspl.taskbazi.common.domain.exception;

public class MissingUserIdException extends DomainException {
    public MissingUserIdException() {
        super("user id is missing!!!");
    }

    @Override
    public String problemKey() {
        return "common.user_id.missing";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
