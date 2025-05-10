package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.ProtectedVerificationCode;

public interface VerificationCodeProtector {
    ProtectedVerificationCode protect(PlainVerificationCode plainVerificationCode);
    boolean matches(PlainVerificationCode plainVerificationCode, ProtectedVerificationCode protectedVerificationCode);
}
