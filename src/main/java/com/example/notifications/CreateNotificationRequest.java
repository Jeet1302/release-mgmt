package com.example.notifications;

import jakarta.validation.constraints.NotBlank;

public record CreateNotificationRequest(
        @NotBlank String customerId,
        @NotBlank String message) {
}
