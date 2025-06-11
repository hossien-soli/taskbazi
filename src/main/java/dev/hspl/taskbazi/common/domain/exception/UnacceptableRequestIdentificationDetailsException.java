package dev.hspl.taskbazi.common.domain.exception;

public class UnacceptableRequestIdentificationDetailsException extends DomainException {
    public UnacceptableRequestIdentificationDetailsException() {
        super("request identification details is unacceptable!");
    }

    @Override
    public String problemKey() {
        return "user.request_identification_details.unacceptable";
    }

    @Override
    public short groupingValue() {
        return 400;
    }
}
