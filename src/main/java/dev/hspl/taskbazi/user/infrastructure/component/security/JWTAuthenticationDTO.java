package dev.hspl.taskbazi.user.infrastructure.component.security;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JWTAuthenticationDTO implements Authentication {
    private final boolean authenticated;

    private final String plainJWTToken;
    private final String requestIdentifier; // ip for web clients

    private final UniversalUser authenticatedUser;

    private JWTAuthenticationDTO(
            boolean authenticated,
            String plainJWTToken,
            String requestIdentifier,
            UniversalUser authenticatedUser
    ) {
        this.authenticated = authenticated;
        this.plainJWTToken = plainJWTToken;
        this.requestIdentifier = requestIdentifier;
        this.authenticatedUser = authenticatedUser;
    }

    public static JWTAuthenticationDTO unauthenticated(
            String plainJWTToken,
            String requestIdentifier
    ) {
        return new JWTAuthenticationDTO(false,plainJWTToken,requestIdentifier,null);
    }

    public static JWTAuthenticationDTO authenticated(
            String requestIdentifier,
            UniversalUser authenticatedUser
    ) {
        return new JWTAuthenticationDTO(true,null,requestIdentifier,authenticatedUser);
    }

    @Override
    public Object getCredentials() {
        return plainJWTToken;
    }

    @Override
    public Object getDetails() {
        return requestIdentifier;
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
