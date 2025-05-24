package dev.hspl.taskbazi.common.infrastructure.message.notification;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.thymeleaf.TemplateEngine;

import java.util.Collection;
import java.util.Set;

public interface NotificationBroadcastRequest {
    Collection<NotificationRecipient> recipients();

    @NonNull
    UserFriendlyMessage prepareMessage(MessageSource messageSource, TemplateEngine htmlTemplateEngine);

    @NonNull
    Set<NotificationDeliveryMethod> deliveryMethods();
}
