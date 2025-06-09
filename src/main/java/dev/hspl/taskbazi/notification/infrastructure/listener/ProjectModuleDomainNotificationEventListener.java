package dev.hspl.taskbazi.notification.infrastructure.listener;

import dev.hspl.taskbazi.common.domain.event.ManagingTaskAssignmentDomainEvent;
import dev.hspl.taskbazi.common.domain.event.ProjectClosedDomainEvent;
import dev.hspl.taskbazi.common.domain.event.ProjectStartedDomainEvent;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationBroadcastRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.handler.NotificationBroadcaster;
import dev.hspl.taskbazi.notification.infrastructure.core.handler.NotificationRequestHandler;
import dev.hspl.taskbazi.notification.infrastructure.request.ManagingTaskAssignmentNotification;
import dev.hspl.taskbazi.notification.infrastructure.request.broadcast.ProjectClosedNotificationBroadcast;
import dev.hspl.taskbazi.notification.infrastructure.request.broadcast.ProjectStartedNotificationBroadcast;
import dev.hspl.taskbazi.user.UserEmailAddressResolverAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;

// Only the Project module's notification request events should be processed here
// Due to our outbox implementation, this operation needs to run inline with the database transaction (not asynchronously)
// if you want async execution, use @TransactionalEventListener to ensure notifications are only sent after successful transaction commit
// Technically, async notification processing even with outboxing isn't strictly forbidden and may boost throughput,...
// ...but it introduces delivery guarantees trade-offs and higher database resource consumption...
// ...because it creates one more database transaction and uses one more connection!!!
// But I think losing a notification is not that important, also it could potentially break the actual application use-case
// So let use async processing!!! :)

@Component
@RequiredArgsConstructor
public class ProjectModuleDomainNotificationEventListener {
    private final NotificationRequestHandler notificationRequestHandler;
    private final NotificationBroadcaster notificationBroadcaster;
    private final UserEmailAddressResolverAPI emailAddressResolver;

    private List<NotificationRecipient> prepareBroadcastRecipients(
            Collection<UserId> notificationUserIds,
            UserRole targetUserRole,
            boolean notificationIsCriticalForDomain
    ) {
        final List<NotificationRecipient> recipients = new ArrayList<>(5);

        if (notificationIsCriticalForDomain) {
            Map<UserId, EmailAddress> resolveResult = emailAddressResolver.resolveMany(
                    targetUserRole,
                    notificationUserIds
            );

            resolveResult.forEach((userId, emailAddress) -> {
                recipients.add(new NotificationRecipient(
                        targetUserRole,
                        userId,
                        emailAddress
                ));
            });

            // check if the user is not in the resolveResult and add just the id of it
            for (UserId userId : notificationUserIds) {
                boolean added = recipients.stream().anyMatch(recipient -> Objects.equals(recipient.userId(),userId));
                if (!added) {
                    recipients.add(new NotificationRecipient(
                            targetUserRole,
                            userId,
                            null
                    ));
                }
            }
        } else {
            for (UserId userId : notificationUserIds) {
                recipients.add(new NotificationRecipient(
                        targetUserRole,
                        userId,
                        null
                ));
            }
        }

        return recipients;
    }

    @TransactionalEventListener(fallbackExecution = true, phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleProjectStartedDomainEvent(ProjectStartedDomainEvent event) {
        Collection<UserId> notificationUserIds = event.notificationUserIds();
        boolean criticalNotification = event.criticalNotification();

        NotificationBroadcastRequest broadcastRequest = new ProjectStartedNotificationBroadcast(
                event.eventOccurredAt(), event.getProjectId(), event.getProjectTitle(),
                prepareBroadcastRecipients(notificationUserIds,event.notificationTargetRole(),criticalNotification),
                criticalNotification
        );

        notificationBroadcaster.broadcast(broadcastRequest);
    }

    @TransactionalEventListener(fallbackExecution = true, phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleProjectCompletedDomainEvent(ProjectClosedDomainEvent event) {
        Collection<UserId> notificationUserIds = event.notificationUserIds();
        boolean criticalNotification = event.criticalNotification();

        NotificationBroadcastRequest broadcastRequest = new ProjectClosedNotificationBroadcast(
                event.eventOccurredAt(), event.getProjectId(), event.getProjectTitle(), event.getCloseStatus(),
                prepareBroadcastRecipients(notificationUserIds,event.notificationTargetRole(),criticalNotification),
                criticalNotification
        );

        notificationBroadcaster.broadcast(broadcastRequest);
    }

    @TransactionalEventListener(fallbackExecution = true, phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleManagingTaskAssignmentDomainEvent(ManagingTaskAssignmentDomainEvent event) {
        NotificationRequest actualRequest = new ManagingTaskAssignmentNotification(
                event.eventOccurredAt(), event.notificationUserId(), event.notificationUserEmailAddress(),
                event.getRelatedProjectId(), event.getRelatedProjectTitle(), event.getTaskTitle(),
                event.getTaskPriority(), event.criticalNotification()
        );

        notificationRequestHandler.handle(actualRequest);
    }
}
