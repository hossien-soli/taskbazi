package dev.hspl.taskbazi.common.domain.exception;

public class UnacceptableDescriptionException extends DomainException {
    public UnacceptableDescriptionException() {
        super("The description is unacceptable. It must be between 10 and 500 characters long.");
    }

    @Override
    public String problemKey() {
        return "common.description.unacceptable";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
