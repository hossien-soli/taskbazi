package dev.hspl.taskbazi.notification.infrastructure.core.delivery.impl;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.notification.infrastructure.email.GlobalEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class EmailNotificationDeliveryAgent implements NotificationDeliveryAgent {
    private final GlobalEmailSender globalEmailSender;

    @Override
    public boolean support(NotificationDeliveryMethod deliveryMethod) {
        return deliveryMethod.equals(NotificationDeliveryMethod.EMAIL);
    }

    @Override
    public boolean tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient) {
        EmailAddress targetEmailAddress = recipient.emailAddress();

        if (targetEmailAddress == null) {
            return false;
        }

        try {
            globalEmailSender.sendEmailMessage(recipient.emailAddress(), message);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
