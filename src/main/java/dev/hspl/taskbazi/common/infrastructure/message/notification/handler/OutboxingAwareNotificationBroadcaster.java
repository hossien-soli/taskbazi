package dev.hspl.taskbazi.common.infrastructure.message.notification.handler;

import dev.hspl.taskbazi.common.infrastructure.message.UserFriendlyMessage;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationBroadcastRequest;
import dev.hspl.taskbazi.common.infrastructure.message.notification.NotificationRecipient;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryAgent;
import dev.hspl.taskbazi.common.infrastructure.message.notification.delivery.NotificationDeliveryMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.util.Collection;
import java.util.List;
import java.util.Set;

// act like OutboxingAwareNotificationRequestHandler for each recipient

@Component
@RequiredArgsConstructor
public class OutboxingAwareNotificationBroadcaster implements NotificationBroadcaster {
    private final List<NotificationDeliveryAgent> deliveryAgents;
    private final MessageSource messageSource; // Necessary for constructing the notification message content
    private final TemplateEngine htmlTemplateEngine; // Necessary for constructing the notification message content

    @Override
    @Transactional
    public void broadcast(NotificationBroadcastRequest broadcastRequest) {
        Collection<NotificationRecipient> recipients = broadcastRequest.recipients();
        UserFriendlyMessage message = broadcastRequest.prepareMessage(messageSource, htmlTemplateEngine);
        Set<NotificationDeliveryMethod> deliveryMethods = broadcastRequest.deliveryMethods();

        for (NotificationDeliveryMethod method : deliveryMethods) {
            for (NotificationDeliveryAgent currentAgent : deliveryAgents) {
                if (currentAgent.support(method)) {
                    for (NotificationRecipient currentRecipient : recipients) {
                        currentAgent.tryDeliver(message, currentRecipient);
                    }
                }
            }
        }
    }
}
