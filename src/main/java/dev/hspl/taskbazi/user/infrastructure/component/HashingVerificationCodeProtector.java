package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.domain.service.VerificationCodeProtector;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.ProtectedVerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashingVerificationCodeProtector implements VerificationCodeProtector {
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProtectedVerificationCode protect(PlainVerificationCode plainVerificationCode) {
        return new ProtectedVerificationCode(passwordEncoder.encode(plainVerificationCode.value()));
    }

    @Override
    public boolean matches(PlainVerificationCode plainVerificationCode, ProtectedVerificationCode protectedVerificationCode) {
        return passwordEncoder.matches(plainVerificationCode.value(),protectedVerificationCode.value());
    }
}
