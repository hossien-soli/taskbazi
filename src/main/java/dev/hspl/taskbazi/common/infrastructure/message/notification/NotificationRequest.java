package dev.hspl.taskbazi.common.infrastructure.message.notification;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.util.Set;

// Modules/Bounded-contexts shall self-contain their notification request implementations and adopt this abstraction

public interface NotificationRequest {
    @NonNull
    NotificationRecipient recipient();

    @NonNull
    UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine);

    @NonNull
    Set<NotificationDeliveryMethod> deliveryMethods();
}
