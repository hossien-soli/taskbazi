package dev.hspl.taskbazi.notification.infrastructure.core.delivery.impl;

import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.notification.application.PersistedNotificationRepository;
import dev.hspl.taskbazi.notification.application.model.PersistedNotification;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.notification.infrastructure.core.exception.MissingDeliveryAgentTargetIdentifierException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// working with the application layer PersistedNotification repository & entity
// because our application PersistedNotification repository is easily replaceable with other persistence tools or databases

@Component
@RequiredArgsConstructor
@Order(1)
public class ApplicationPersistedNotificationDeliveryAgent implements NotificationDeliveryAgent {
    private final PersistedNotificationRepository applicationRepository;

    @Override
    public boolean support(NotificationDeliveryMethod deliveryMethod) {
        return deliveryMethod.equals(NotificationDeliveryMethod.PERSISTED);
    }

    @Override
    public void tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient) {
        UserId targetUserId = recipient.userId();
        if (targetUserId == null) {
            throw new MissingDeliveryAgentTargetIdentifierException("target user-id for persisted-notification-delivery-agent is missing");
        }

        PersistedNotification notification = PersistedNotification.newNotification(
                targetUserId.value(),
                message.subject(),
                message.plainTextBody(),
                message.htmlBody(),
                message.isImportant()
        );

        applicationRepository.saveNewNotification(notification,recipient.userRole());
    }
}
