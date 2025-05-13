package dev.hspl.taskbazi.common.infrastructure.component;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.domain.DomainAggregateRoot;
import dev.hspl.taskbazi.common.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SpringEventBusDomainEventPublisher implements GlobalDomainEventPublisher {
    private final ApplicationEventPublisher springEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        springEventPublisher.publishEvent(event);
    }

    @Override
    public void publishAll(Collection<? extends DomainEvent> events) {
        for (DomainEvent event : events) { publish(event); }
    }

    @Override
    public void publishAll(DomainAggregateRoot aggregateRoot) {
        publishAll(aggregateRoot.domainEvents());
        aggregateRoot.clearDomainEvents();
    }
}
