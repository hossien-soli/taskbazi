package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.UserFullName;

import java.time.LocalDateTime;

// Email delivery should be handled through an outbox pattern implementation

public interface ClientRegistrationEmailSender {
    void sendVerificationEmail(
            EmailAddress emailAddress,
            PlainVerificationCode verificationCode,
            int sessionLifetimeSeconds,
            LocalDateTime sessionValidUntil,
            UserFullName clientFullName
    );
}
