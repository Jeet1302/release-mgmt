package com.example.notifications;

import java.time.Instant;
import java.util.UUID;

public record Notification(
        UUID id,
        String customerId,
        String message,
        Instant createdAt) {
}
