package dev.hspl.taskbazi.project.domain.exception;

import dev.hspl.taskbazi.common.domain.DomainException;
import lombok.Getter;

// you should extract the properties of this class for generating appropriate message for user

@Getter
public class ProjectRegistrationRestrictionException extends DomainException {
    private final int registrationLimitationDelaySeconds;
    private final long secondsToNextAllowedRegistration;

    public ProjectRegistrationRestrictionException(
            int registrationLimitationDelaySeconds,
            long secondsToNextAllowedRegistration
    ) {
        super("There is a %d-second delay restriction for registering a new project(delay between each project registration)".formatted(
                registrationLimitationDelaySeconds
        ));

        this.registrationLimitationDelaySeconds = registrationLimitationDelaySeconds;
        this.secondsToNextAllowedRegistration = secondsToNextAllowedRegistration;
    }

    @Override
    public String problemKey() {
        return "project.registration.restriction";
    }
}
