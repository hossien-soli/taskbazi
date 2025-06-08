package dev.hspl.taskbazi.notification.infrastructure.request;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Set;

// only for clients!!!

public record RegistrationWelcomeMessageNotification(
        LocalDateTime registrationDateTime,
        UserId recipientUserId,
        EmailAddress recipientEmailAddress,
        String clientFullName,
        Username clientUsername,
        boolean isCriticalForDomain
) implements NotificationRequest {
    @Override
    @NonNull
    public NotificationRecipient recipient() {
        return new NotificationRecipient(
                UserRole.CLIENT,
                recipientUserId,
                recipientEmailAddress
        );
    }

    @Override
    @NonNull
    public UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine) {
        // TODO: build a pretty registration welcome message
        return new UserFriendlyMessage("welcome baby", "thanx for your registration!!!", "<h2>ich liebe dich</h2>", isCriticalForDomain);
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
