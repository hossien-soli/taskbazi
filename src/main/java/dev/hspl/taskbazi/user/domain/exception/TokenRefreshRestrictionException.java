package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;
import lombok.Getter;

@Getter
public class TokenRefreshRestrictionException extends DomainException {
    private final int tokenRefreshDelaySeconds;

    public TokenRefreshRestrictionException(int tokenRefreshDelaySeconds) {
        super("There is a %d-second delay restriction for refreshing tokens! (delay after creation)".formatted(
                tokenRefreshDelaySeconds
        ));

        this.tokenRefreshDelaySeconds = tokenRefreshDelaySeconds;
    }

    @Override
    public String problemKey() {
        return "unauthorized_request";
    }
}
