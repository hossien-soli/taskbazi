package dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.impl;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.email.GlobalEmailSender;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import dev.hspl.taskbazi.common.infrastructure.message.notification.exception.MissingDeliveryAgentTargetIdentifierException;
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
