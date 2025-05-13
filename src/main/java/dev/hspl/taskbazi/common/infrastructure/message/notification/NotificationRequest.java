package dev.hspl.taskbazi.common.infrastructure.message.notification;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.thymeleaf.TemplateEngine;

import java.util.Set;

// Modules/Bounded-contexts shall self-contain their notification request implementations and adopt this abstraction

public interface NotificationRequest {
    NotificationRecipient recipient();

    UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine);

    Set<NotificationDeliveryMethod> deliveryMethods();
}
