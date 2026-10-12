package com.example.notifications;

import java.time.Instant;
import java.util.UUID;

public record NotificationCreatedEvent(
        UUID notificationId,
        String customerId,
        String message,
        Instant createdAt) {

    public static NotificationCreatedEvent from(Notification notification) {
        return new NotificationCreatedEvent(
                notification.id(),
                notification.customerId(),
                notification.message(),
                notification.createdAt());
    }
}
