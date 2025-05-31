package dev.hspl.taskbazi.user.infrastructure.presentation.web.security;

import dev.hspl.taskbazi.common.application.AuthenticatedUserNotFoundException;
import dev.hspl.taskbazi.common.application.AuthenticatedUserProvider;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GlobalAuthenticatedUserProvider implements AuthenticatedUserProvider {
    @Override
    public UniversalUser currentAuthUser() throws AuthenticatedUserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JWTAuthenticationDTO) {
            return (UniversalUser) authentication.getPrincipal();
        }

        throw new AuthenticatedUserNotFoundException();
    }
}
