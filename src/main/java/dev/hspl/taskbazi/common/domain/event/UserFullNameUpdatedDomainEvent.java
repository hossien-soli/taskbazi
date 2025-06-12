package dev.hspl.taskbazi.common.domain.event;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

// if you don't have eventual consistency mechanisms inside monolithic system(current):
// update listeners to this event(modules that need this for updating their copy of fullName) should not be async @Async!!!
// they should run inside current active transaction for consistency between modules

@RequiredArgsConstructor
@ToString
public class UserFullNameUpdatedDomainEvent implements DomainEvent {
    private final LocalDateTime currentDateTime;

    @Getter
    private final UserId userId;

    @Getter
    private final UserRole userRole;

    @Getter
    private final String oldFullName;

    @Getter
    private final String newFullName;

    @Getter
    private final int entityVersion; // version before new-update

    @Override
    public LocalDateTime eventOccurredAt() {
        return currentDateTime;
    }
}
