package dev.hspl.taskbazi.user.infrastructure.config;

import dev.hspl.taskbazi.user.infrastructure.component.security.JWTAccessTokenService;
import dev.hspl.taskbazi.user.infrastructure.component.security.JWTAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(JWTAccessTokenService jwtAccessTokenService) {
        ProviderManager manager = new ProviderManager(
                new JWTAuthenticationProvider(jwtAccessTokenService)
        );

        manager.setEraseCredentialsAfterAuthentication(true);
        //manager.setAuthenticationEventPublisher(authenticationEventPublisher);
        return manager;
    }
}
