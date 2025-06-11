package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.exception.DomainException;
import lombok.Getter;

@Getter
public class TooManyActiveLoginSessionException extends DomainException {
    private final short maxAllowedActiveSession;

    public TooManyActiveLoginSessionException(short maxAllowedActiveSessions) {
        super("Too many active login session exception!");
        this.maxAllowedActiveSession = maxAllowedActiveSessions;
    }

    @Override
    public String problemKey() {
        return "user.login.too_many_session";
    }

    @Override
    public short groupingValue() {
        return 429;
    }
}
