package dev.hspl.taskbazi.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration(proxyBeanMethods = false)
@EnableAsync
public class MasterConfig {
    @Bean
    public SecureRandom applicationSecureRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }
}
