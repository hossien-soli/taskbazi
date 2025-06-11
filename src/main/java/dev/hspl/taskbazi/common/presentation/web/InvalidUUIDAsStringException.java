package dev.hspl.taskbazi.common.presentation.web;

import dev.hspl.taskbazi.common.domain.exception.ProblemAware;

public class InvalidUUIDAsStringException extends RuntimeException implements ProblemAware {
    public InvalidUUIDAsStringException() {
        super("one plain string uuid provided by user is invalid!");
    }

    @Override
    public String problemKey() {
        return "invalid_uuid_string";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
