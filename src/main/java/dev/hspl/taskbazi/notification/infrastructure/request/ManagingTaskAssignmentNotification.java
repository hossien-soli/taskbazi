package dev.hspl.taskbazi.notification.infrastructure.request;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

// sends when a task is assigned to a collaborator by owner or managingAssignment collaborator to another collaborator

public record ManagingTaskAssignmentNotification(
        LocalDateTime assignmentDateTime,
        UserId recipientClientId,
        EmailAddress recipientEmailAddress, // client email address
        UUID relatedProjectId,
        String relatedProjectTitle,
        String taskTitle,
        String taskPriority,
        boolean isCriticalForDomain
) implements NotificationRequest {
    @Override
    @NonNull
    public NotificationRecipient recipient() {
        return new NotificationRecipient(
                UserRole.CLIENT, // always is a client
                recipientClientId,
                recipientEmailAddress
        );
    }

    @Override
    @NonNull
    public UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine) {
        return new UserFriendlyMessage("task assignment", "new task assigned to you", "<h2>new task assigned to you</h2>", isCriticalForDomain);
    }

    @Override
    @NonNull
    public Set<NotificationDeliveryMethod> deliveryMethods() {
        if (isCriticalForDomain) {
            return Set.of(NotificationDeliveryMethod.PERSISTED, NotificationDeliveryMethod.EMAIL);
        }

        return Set.of(NotificationDeliveryMethod.PERSISTED);
    }
}
