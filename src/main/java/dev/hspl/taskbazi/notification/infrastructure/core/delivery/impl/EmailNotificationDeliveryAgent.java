package dev.hspl.taskbazi.notification.infrastructure.core.delivery.impl;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.email.GlobalEmailSender;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.notification.infrastructure.core.exception.MissingDeliveryAgentTargetIdentifierException;
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
    public void tryDeliver(UserFriendlyMessage message, NotificationRecipient recipient) {
        EmailAddress targetEmailAddress = recipient.emailAddress();

        if (targetEmailAddress == null) {
            throw new MissingDeliveryAgentTargetIdentifierException("target email-address for email-notification-delivery-agent is missing!");
        }

        globalEmailSender.sendEmailMessage(recipient.emailAddress(),message);
    }
}
