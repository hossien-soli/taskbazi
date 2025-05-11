package dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.impl;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.common.infrastructure.persistence.entity.PersistedNotification;
import dev.hspl.taskbazi.common.infrastructure.persistence.repository.PersistedNotificationJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// don't mark the tryDeliver method as @Transactional (we should catch the failure inside the method)

@Component
@RequiredArgsConstructor
public class PersistedNotificationDeliveryAgent implements NotificationDeliveryAgent {
    private final PersistedNotificationJPARepository jpaRepository;

    @Override
    public boolean support(NotificationDeliveryMethod deliveryMethod) {
        return deliveryMethod.equals(NotificationDeliveryMethod.PERSISTED);
    }

    @Override
    public boolean tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient) {
        try {
            UserRole userRole = recipient.userRole();
            if (!userRole.equals(UserRole.CLIENT)) return false;
            // notification handling for other type of users not implemented yet!!
            // but we should store them also in a shared database table(persisted_notification)
            // because we are storing all roles in a shared database table(users)

            PersistedNotification notification = new PersistedNotification();
            notification.setUserId(recipient.userId().value());
            notification.setSubject(message.subject());
            notification.setMessage(message.plainTextBody());
            notification.setHtmlMessage(message.htmlBody());
            notification.setImportantNotification(message.isImportant());

            jpaRepository.save(notification); // this should run inside its own transaction (Errors should be handled internally and not propagate to outer layers)
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
