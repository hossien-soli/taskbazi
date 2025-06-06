package dev.hspl.taskbazi.project.infrastructure.message;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRequest;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.project.domain.value.ProjectId;
import dev.hspl.taskbazi.project.domain.value.ProjectTitle;
import dev.hspl.taskbazi.project.domain.value.TaskPriority;
import dev.hspl.taskbazi.project.domain.value.TaskTitle;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Set;

// sends when a task is assigned to a collaborator by owner or managingAssignment collaborator to another collaborator

public record ManagingTaskAssignmentNotification(
        LocalDateTime assignmentDateTime,
        UserId recipientClientId,
        EmailAddress recipientEmailAddress, // client email address
        ProjectId relatedProjectId,
        ProjectTitle relatedProjectTitle,
        TaskTitle taskTitle,
        TaskPriority taskPriority,
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
        return new UserFriendlyMessage("task assignment","new task assigned to you","<h2>new task assigned to you</h2>",isCriticalForDomain);
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
