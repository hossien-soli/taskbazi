package dev.hspl.taskbazi.user.presentation.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
                    requests.requestMatchers(
                            "/client/register/request",
                            "/client/register/finalize",
                            "/client/login/new",
                            "/client/login/follow"
                    ).permitAll();

                    requests.requestMatchers(
                            "/client/project/register",
                            "/client/project/*/start"
                    ).hasRole("CLIENT"); // this role also checked in the domain layer!!
                    // maybe we should remove this role check for respecting the domain rules!!!

                    requests.anyRequest().authenticated();
                })
                .addFilterBefore(
                        new JWTAuthenticationHttpFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
