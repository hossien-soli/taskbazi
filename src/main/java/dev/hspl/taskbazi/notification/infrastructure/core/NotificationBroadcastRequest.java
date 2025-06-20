package dev.hspl.taskbazi.notification.infrastructure.core;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.util.Collection;
import java.util.Set;

public interface NotificationBroadcastRequest {
    @NonNull
    Collection<NotificationRecipient> recipients();

    @NonNull
    UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine);

    @NonNull
    Set<NotificationDeliveryMethod> deliveryMethods();
}
