package dev.hspl.taskbazi.user.infrastructure.presentation.web.security;

import dev.hspl.taskbazi.user.infrastructure.component.JWTAccessTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain defaultSFC(
            HttpSecurity http,
            AuthenticationManager authenticationManager
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests.anyRequest().authenticated();
                })
                .addFilterBefore(
                        new JWTAuthenticationHttpFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
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
