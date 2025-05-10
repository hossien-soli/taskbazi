package dev.hspl.taskbazi.user.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;
import lombok.Getter;

// you should extract the properties of this class for generating appropriate message for user

@Getter
public class RegistrationSessionRestrictionException extends DomainException {
    private final int sessionLimitationDelay;
    private final int secondsToNextSession;

    public RegistrationSessionRestrictionException(int sessionLimitationDelay, int secondsToNextSession) {
        super("There is a %d-second delay restriction for creating a new registration session or sending a verification email.".formatted(
                sessionLimitationDelay
        ));

        this.sessionLimitationDelay = sessionLimitationDelay;
        this.secondsToNextSession = secondsToNextSession;
    }

    @Override
    public String problemKey() {
        return "user.registration_session.restriction_problem";
    }
}
