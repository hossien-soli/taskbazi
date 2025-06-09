package dev.hspl.taskbazi.notification.infrastructure.core.handler;

import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRecipient;
import dev.hspl.taskbazi.notification.infrastructure.core.NotificationRequest;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.notification.infrastructure.core.delivery.NotificationDeliveryMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.util.List;
import java.util.Set;

// because we have email outboxing and push-notification(native-app) outboxing it is better to...
// ...call handler inside an active database transaction using OutboxingAwareNotificationRequestHandler
// otherwise never call these heavy operations(sending email or push-notification) inside an active database transaction
// in fact this implementation sends PersistedNotification directly(if we have OutboxingGlobalEmailSender and push-notification outboxing)
// direct = It means the delivery logic executes/calls directly within the current active transaction, rather than asynchronously (async) or through any other execution method
// OutboxingAware? = knows that email sender is outboxed and native-app push-notification sender is also outboxed

@Component
@RequiredArgsConstructor
public class OutboxingAwareNotificationRequestHandler implements NotificationRequestHandler {
    private final List<NotificationDeliveryAgent> deliveryAgents;
    private final MessageSource messageSource; // Necessary for constructing the notification message content
    private final TemplateEngine htmlTemplateEngine; // Necessary for constructing the notification message content

    @Override
    @Transactional // because all delivery agents are working with our primary database!!
    public byte handle(NotificationRequest request) {
        NotificationRecipient recipient = request.recipient();
        UserFriendlyMessage message = request.prepareMessage(messageSource, htmlTemplateEngine);
        Set<NotificationDeliveryMethod> deliveryMethods = request.deliveryMethods();

        byte successCount = 0;
        for (NotificationDeliveryMethod method : deliveryMethods) {
            for (NotificationDeliveryAgent currentAgent : this.deliveryAgents) {
                if (currentAgent.support(method)) {
                    if (currentAgent.tryDeliver(message, recipient)) {
                        successCount++;
                    }
                }
            }
        }

        return successCount;
    }
}
