package dev.hspl.taskbazi.notification.infrastructure.request;

import dev.hspl.taskbazi.common.domain.value.*;
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

public record AccountLoginAlertNotification(
        LocalDateTime loginDateTime,
        UserRole recipientUserRole,
        UserId recipientUserId,
        EmailAddress recipientEmailAddress,
        RequestClientIdentifier requestClientIdentifier, // required
        RequestIdentificationDetails requestIdentificationDetails, // nullable
        UUID newLoginSessionId, // required
        boolean isCriticalForDomain
) implements NotificationRequest {
    @Override
    @NonNull
    public NotificationRecipient recipient() {
        return new NotificationRecipient(
                recipientUserRole,
                recipientUserId,
                recipientEmailAddress
        );
    }

    @Override
    @NonNull
    public UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine) {
        // TODO: build the account login alert message body
        return new UserFriendlyMessage("new login alert","new login alert",null,isCriticalForDomain);
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
