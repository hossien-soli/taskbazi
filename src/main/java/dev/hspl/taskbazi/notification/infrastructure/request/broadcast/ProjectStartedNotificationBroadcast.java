package dev.hspl.taskbazi.notification.infrastructure.request.broadcast;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationBroadcastRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ProjectStartedNotificationBroadcast(
        LocalDateTime startDateTime,
        UUID projectId,
        String projectTitle,
        List<NotificationRecipient> targetRecipients,
        boolean criticalForDomain
) implements NotificationBroadcastRequest {
    @Override
    @NonNull
    public Collection<NotificationRecipient> recipients() {
        return targetRecipients;
    }

    @Override
    @NonNull
    public UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine) {
        return new UserFriendlyMessage("project started","one project started","<h2>start alert!</h2>",criticalForDomain);
    }

    @Override
    @NonNull
    public Set<NotificationDeliveryMethod> deliveryMethods() {
        if (criticalForDomain) {
            return Set.of(NotificationDeliveryMethod.PERSISTED, NotificationDeliveryMethod.EMAIL);
        }

        return Set.of(NotificationDeliveryMethod.PERSISTED);
    }
}
