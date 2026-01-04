package dev.hspl.taskbazi.user.presentation.web.security;

import dev.hspl.taskbazi.user.infrastructure.component.security.JWTAuthenticationDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthenticationHttpFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    private static final String TOKEN_HEADER_NAME = "Authorization";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String targetHeaderValue = request.getHeader(TOKEN_HEADER_NAME);
        boolean validate = targetHeaderValue != null && targetHeaderValue.startsWith("Bearer ");
        if (validate) {
            String actualJWTToken = targetHeaderValue.substring(7);
            validate = actualJWTToken.length() >= 30;
            if (validate) {
                try {
                    Authentication authRequest = JWTAuthenticationDTO.unauthenticated(
                            actualJWTToken,
                            request.getRemoteAddr()
                    );

                    Authentication authResult = authenticationManager.authenticate(authRequest);
                    SecurityContext newContext = SecurityContextHolder.createEmptyContext();
                    newContext.setAuthentication(authResult);
                    SecurityContextHolder.setContext(newContext);
                } catch (AuthenticationException exception) {
                    // do nothing just let filter chain keep handling remaining filters
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
