package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.domain.service.PasswordProtector;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.ProtectedPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashingPasswordProtector implements PasswordProtector {
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProtectedPassword protect(PlainPassword plainPassword) {
        return new ProtectedPassword(passwordEncoder.encode(plainPassword.value()));
    }

    @Override
    public boolean matches(PlainPassword plainPassword, ProtectedPassword protectedPassword) {
        return passwordEncoder.matches(plainPassword.value(),protectedPassword.value());
    }
}
