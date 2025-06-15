package dev.hspl.taskbazi.common.presentation.web;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public abstract class ControllerUtils {
    protected UUID validateUUIDString(String uuidString) {
        if (uuidString == null || uuidString.length() != 36) {
            throw new InvalidUUIDAsStringException();
        }

        try {
            return UUID.fromString(uuidString);
        } catch (Exception exception) {
            throw new InvalidUUIDAsStringException();
        }
    }

    protected UniversalUser validateAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UniversalUser authenticatedUser) {
            return authenticatedUser;
        }

        throw new UnsupportedAuthenticationSecurityException();
    }
}
