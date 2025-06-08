package dev.hspl.taskbazi.notification.infrastructure.core;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
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
