package dev.hspl.taskbazi.common.domain.event;

import java.time.LocalDateTime;

// due to AbstractAggregateRoot's dependency on Spring Data repositories for event publishing, I decided to develop a custom event management solution
// org.springframework.data.domain.AbstractAggregateRoot

public interface DomainEvent {
    LocalDateTime eventOccurredAt();
}
