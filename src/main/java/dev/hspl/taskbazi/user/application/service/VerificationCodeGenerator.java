package dev.hspl.taskbazi.user.application.service;

import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;

public interface VerificationCodeGenerator {
    PlainVerificationCode generateNew();
}
