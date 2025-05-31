package dev.hspl.taskbazi.user.infrastructure.component.security;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.user.domain.exception.InvalidAccessTokenException;
import dev.hspl.taskbazi.user.domain.value.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {
    private final JWTAccessTokenService jwtAccessTokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String plainJWTToken = (String) authentication.getCredentials();
        String requestIdentifier = (String) authentication.getDetails();

        try {
            UniversalUser extractedUser = jwtAccessTokenService.validateTokenAndExtractUserInfo(
                    new AccessToken(plainJWTToken)
            );

            return JWTAuthenticationDTO.authenticated(requestIdentifier,extractedUser);
        } catch (InvalidAccessTokenException exception) {
            throw new BadCredentialsException("jwt access token is invalid!!!!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationDTO.class.isAssignableFrom(authentication);
    }
}
