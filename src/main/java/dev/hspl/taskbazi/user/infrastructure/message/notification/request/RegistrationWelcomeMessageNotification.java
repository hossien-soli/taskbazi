package dev.hspl.taskbazi.user.infrastructure.message.notification.request;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRequest;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.user.domain.value.ClientFullName;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Set;

// only for clients!!!

public record RegistrationWelcomeMessageNotification(
        LocalDateTime registrationDateTime,
        UserRole recipientUserRole,
        UserId recipientUserId,
        EmailAddress recipientEmailAddress,
        ClientFullName clientFullName,
        Username clientUsername
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
        // TODO: build a pretty registration welcome message
        return new UserFriendlyMessage("welcome baby","thanx for your registration!!!","<h2>ich liebe dich</h2>",false);
    }

    @Override
    @NonNull
    public Set<NotificationDeliveryMethod> deliveryMethods() {
        return Set.of(NotificationDeliveryMethod.PERSISTED, NotificationDeliveryMethod.EMAIL);
    }
}
