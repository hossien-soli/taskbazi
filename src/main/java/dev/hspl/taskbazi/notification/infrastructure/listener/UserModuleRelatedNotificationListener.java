package dev.hspl.taskbazi.notification.infrastructure.listener;

import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.handler.NotificationRequestHandler;
import dev.hspl.taskbazi.notification.infrastructure.request.AccountLoginAlertNotification;
import dev.hspl.taskbazi.notification.infrastructure.request.RegistrationWelcomeMessageNotification;
import dev.hspl.taskbazi.common.domain.event.ClientRegisteredDomainEvent;
import dev.hspl.taskbazi.common.domain.event.NewAccountLoginDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

// Only the User module's notification request events should be processed here
// Due to our outbox implementation, this operation needs to run inline with the database transaction (not asynchronously)
// if you want async execution, use @TransactionalEventListener to ensure notifications are only sent after successful transaction commit
// Technically, async notification processing even with outboxing isn't strictly forbidden and may boost throughput,...
// ...but it introduces delivery guarantees trade-offs and higher database resource consumption...
// ...because it creates one more database transaction and uses one more connection!!!
// But I think losing a notification is not that important, also it could potentially break the actual application use-case
// So let use async processing!!! :)

@Component
@RequiredArgsConstructor
public class UserModuleRelatedNotificationListener {
    private final NotificationRequestHandler notificationRequestHandler;

    @TransactionalEventListener(fallbackExecution = true, phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleNewLoginDomainEvent(NewAccountLoginDomainEvent event) {
        NotificationRequest actualRequest = new AccountLoginAlertNotification(
                event.eventOccurredAt(),
                event.notificationTargetRole(),
                event.notificationUserId(),
                event.notificationUserEmailAddress(),
                event.getDataRequestClientIdentifier(),
                event.getDataRequestIdentificationDetails(),
                event.getDataNewLoginSessionId(),
                event.criticalNotification()
        );

        notificationRequestHandler.handle(actualRequest);
    }

    @TransactionalEventListener(fallbackExecution = true, phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleClientRegisteredDomainEvent(ClientRegisteredDomainEvent event) {
        NotificationRequest actualRequest = new RegistrationWelcomeMessageNotification(
                event.eventOccurredAt(),
                event.notificationUserId(),
                event.notificationUserEmailAddress(),
                event.getClientFullName(),
                event.getClientUsername(),
                event.criticalNotification()
        );

        notificationRequestHandler.handle(actualRequest);
    }

    // TODO: send refresh token reuse detection alert notification to the user
}
