package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.domain.service.OpaqueTokenProtector;
import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;
import dev.hspl.taskbazi.user.domain.value.ProtectedOpaqueToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderOpaqueTokenProtector implements OpaqueTokenProtector {
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProtectedOpaqueToken protect(PlainOpaqueToken plainOpaqueToken) {
        return new ProtectedOpaqueToken(passwordEncoder.encode(plainOpaqueToken.value()));
    }

    @Override
    public boolean matches(PlainOpaqueToken plainOpaqueToken, ProtectedOpaqueToken protectedOpaqueToken) {
        return passwordEncoder.matches(plainOpaqueToken.value(),protectedOpaqueToken.value());
    }
}
