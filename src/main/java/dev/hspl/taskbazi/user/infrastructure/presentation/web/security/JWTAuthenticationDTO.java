package dev.hspl.taskbazi.user.infrastructure.presentation.web.security;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JWTAuthenticationDTO implements Authentication {
    private final boolean authenticated;

    private final String plainJWTToken;
    private final String requestIP;

    private final UniversalUser authenticatedUser;

    private JWTAuthenticationDTO(
            boolean authenticated,
            String plainJWTToken,
            String requestIP,
            UniversalUser authenticatedUser
    ) {
        this.authenticated = authenticated;
        this.plainJWTToken = plainJWTToken;
        this.requestIP = requestIP;
        this.authenticatedUser = authenticatedUser;
    }

    public static JWTAuthenticationDTO unauthenticated(
            String plainJWTToken,
            String requestIP
    ) {
        return new JWTAuthenticationDTO(false,plainJWTToken,requestIP,null);
    }

    public static JWTAuthenticationDTO authenticated(
            String requestIP,
            UniversalUser authenticatedUser
    ) {
        return new JWTAuthenticationDTO(true,null,requestIP,authenticatedUser);
    }

    @Override
    public Object getCredentials() {
        return plainJWTToken;
    }

    @Override
    public Object getDetails() {
        return requestIP;
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }

    @Override
    public String getName() {
        return authenticatedUser.universalUserId().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + authenticatedUser.userRole()));
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("unsupported operation!!!!!");
    }
}
