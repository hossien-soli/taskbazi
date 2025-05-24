package dev.hspl.taskbazi.project.infrastructure.message;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.handler.NotificationBroadcaster;
import dev.hspl.taskbazi.common.infrastructure.message.notification.handler.NotificationRequestHandler;
import dev.hspl.taskbazi.project.domain.event.ProjectCompletedDomainEvent;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import dev.hspl.taskbazi.user.UserEmailAddressResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    private final UserEmailAddressResolver emailAddressResolver;

    @TransactionalEventListener(fallbackExecution = true, phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleProjectCompletedDomainEvent(ProjectCompletedDomainEvent event) {
        LocalDateTime completedAt = event.eventOccurredAt();
        ProjectId projectId = event.getProjectId();
        ProjectTitle projectTitle = event.getProjectTitle();
        UserId projectOwner = event.getProjectOwner();

        Collection<UserId> notificationUserIds = event.notificationUserIds();
        boolean criticalNotification = event.criticalNotification();

        List<NotificationRecipient> recipients = new ArrayList<>(5);

        if (criticalNotification) {
            Map<UserId, EmailAddress> resolveResult = emailAddressResolver.resolveMany(
                    event.notificationTargetRole(),
                    notificationUserIds
            );

            resolveResult.forEach((userId, emailAddress) -> {
                recipients.add(new NotificationRecipient(
                        event.notificationTargetRole(),
                        userId,
                        emailAddress
                ));
            });
        } else {
            for (UserId userId : notificationUserIds) {
                recipients.add(new NotificationRecipient(
                        event.notificationTargetRole(),
                        userId,
                        null
                ));
            }
        }

        // TODO: implement BroadcastRequest and broadcast it!
    }
}
