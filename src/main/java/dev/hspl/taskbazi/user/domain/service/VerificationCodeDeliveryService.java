package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.UserFullName;

import java.time.LocalDateTime;

// Email delivery should be handled through an outbox pattern implementation

public interface VerificationCodeDeliveryService {
    void deliver(
            String deliveryTarget,
            PlainVerificationCode verificationCode,
            int sessionLifetimeSeconds,
            LocalDateTime sessionValidUntil,
            UserFullName clientFullName
    );
}
