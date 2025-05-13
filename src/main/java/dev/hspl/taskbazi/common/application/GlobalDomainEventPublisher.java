package dev.hspl.taskbazi.common.application;

import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainEvent;

import java.util.Collection;

// publishing domain-events is an application level concern!!
// due to AbstractAggregateRoot's dependency on Spring Data repositories for event publishing, I decided to develop a custom event management solution
// org.springframework.data.domain.AbstractAggregateRoot

public interface GlobalDomainEventPublisher {
    void publish(DomainEvent event);

    void publishAll(Collection<? extends DomainEvent> events);

    void publishAll(DomainAggregateRoot aggregateRoot);
}
