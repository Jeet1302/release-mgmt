package com.example.notifications;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationPublisher notificationPublisher;
    private final List<Notification> notifications = new CopyOnWriteArrayList<>();

    public NotificationService(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    public Notification create(CreateNotificationRequest request) {
        Notification notification = new Notification(
                UUID.randomUUID(), request.customerId(), request.message(), Instant.now());
        notifications.add(notification);
        notificationPublisher.publish(notification);
        return notification;
    }

    public List<Notification> findByCustomer(String customerId) {
        return notifications.stream()
                .filter(notification -> notification.customerId().equals(customerId))
                .toList();
    }
}
