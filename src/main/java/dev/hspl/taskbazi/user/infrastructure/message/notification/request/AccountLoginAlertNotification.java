package dev.hspl.taskbazi.user.infrastructure.message.notification.request;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRequest;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.user.domain.value.RequestIdentificationDetails;
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
        UUID newLoginSessionId // required
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
        return new UserFriendlyMessage("new login alert","new login alert",null,true);
    }

    @Override
    @NonNull
    public Set<NotificationDeliveryMethod> deliveryMethods() {
        return Set.of(NotificationDeliveryMethod.PERSISTED, NotificationDeliveryMethod.EMAIL);
    }
}
