package dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.impl;

import dev.hspl.taskbazi.common.infrastructure.message.email.GlobalEmailSender;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationDeliveryAgent implements NotificationDeliveryAgent {
    private final GlobalEmailSender globalEmailSender;

    @Override
    public boolean support(NotificationDeliveryMethod deliveryMethod) {
        return deliveryMethod.equals(NotificationDeliveryMethod.EMAIL);
    }

    @Override
    public boolean tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient) {
        try {
            globalEmailSender.sendEmailMessage(recipient.emailAddress(),message);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
