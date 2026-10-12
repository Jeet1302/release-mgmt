package com.example.notifications;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final KafkaTemplate<String, NotificationCreatedEvent> kafkaTemplate;
    private final String topicName;

    public NotificationPublisher(
            KafkaTemplate<String, NotificationCreatedEvent> kafkaTemplate,
            @Value("${notification.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void publish(Notification notification) {
        NotificationCreatedEvent event = NotificationCreatedEvent.from(notification);
        kafkaTemplate.send(topicName, notification.customerId(), event);
    }
}
