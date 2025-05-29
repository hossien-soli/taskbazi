package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;
import lombok.Getter;

// you can extract the maxAllowedProjectInstance value and send a proper message to users

@Getter
public class TooManyProjectInstanceException extends DomainException {
    private final short maxAllowedProjectInstance;

    public TooManyProjectInstanceException(short maxAllowedProjectInstance) {
        super("The number of projects associated with the account has reached the maximum allowed limit(%d)".formatted(
                maxAllowedProjectInstance
        ));

        this.maxAllowedProjectInstance = maxAllowedProjectInstance;
    }

    @Override
    public String problemKey() {
        return "project.registration.too_many_instance";
    }
}
