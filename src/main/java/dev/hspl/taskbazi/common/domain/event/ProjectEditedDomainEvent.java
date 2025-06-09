package dev.hspl.taskbazi.common.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@ToString
public class ProjectEditedDomainEvent implements DomainEvent {
    private final LocalDateTime currentDateTime;

    @Getter
    private final UUID projectId;

    @Getter
    private final String oldProjectTitle;

    @Getter
    private final String newProjectTitle;

    @Getter
    private final String oldDescription; // this is a nullable or optional field for project entity

    @Getter
    private final String newDescription; // this is a nullable or optional field for project entity

    @Getter
    private final int entityVersion; // version before new-update

    @Override
    public LocalDateTime eventOccurredAt() {
        return currentDateTime;
    }
}
