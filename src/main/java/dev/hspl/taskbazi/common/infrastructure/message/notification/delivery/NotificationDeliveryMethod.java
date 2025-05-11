package dev.hspl.taskbazi.common.infrastructure.message.notification.delivery;

public enum NotificationDeliveryMethod {
    PERSISTED, // stored notifications in a database (user can fetch them)
    EMAIL,
    NATIVE_APPLICATION // native front-end(client) applications for android or windows (pushing notification)
}
