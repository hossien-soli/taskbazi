package dev.hspl.taskbazi.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration(proxyBeanMethods = false)
public class MasterConfig {

    @Bean
    public SecureRandom applicationSecureRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
