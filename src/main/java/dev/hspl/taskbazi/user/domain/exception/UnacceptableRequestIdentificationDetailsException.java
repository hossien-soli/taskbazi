package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;

public class UnacceptableRequestIdentificationDetailsException extends DomainException {
    public UnacceptableRequestIdentificationDetailsException() {
        super("request identification details is unacceptable!");
    }

    @Override
    public String problemKey() {
        return "user.request_identification_details.unacceptable";
    }
}
