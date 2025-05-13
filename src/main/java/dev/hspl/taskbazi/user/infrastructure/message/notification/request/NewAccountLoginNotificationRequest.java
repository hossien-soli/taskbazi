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
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class NewAccountLoginNotificationRequest implements NotificationRequest {
    private final LocalDateTime loginDateTime;

    private final UserRole recipientUserRole;
    private final UserId recipientUserId;
    private final EmailAddress recipientEmailAddress;

    private final RequestClientIdentifier requestClientIdentifier; // required
    private final RequestIdentificationDetails requestIdentificationDetails; // nullable
    private final UUID newLoginSessionId; // required

    @Override
    public NotificationRecipient recipient() {
        return new NotificationRecipient(
                recipientUserRole,
                recipientUserId,
                recipientEmailAddress
        );
    }

    @Override
    public UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine) {
        if (requestIdentificationDetails != null) {

        }

        return new UserFriendlyMessage();
    }

    @Override
    public Set<NotificationDeliveryMethod> deliveryMethods() {
        return Set.of(NotificationDeliveryMethod.PERSISTED, NotificationDeliveryMethod.EMAIL);
    }
}
