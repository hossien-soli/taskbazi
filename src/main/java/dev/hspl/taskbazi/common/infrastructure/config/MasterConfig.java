package dev.hspl.taskbazi.common.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

@Configuration(proxyBeanMethods = false)
@EnableAsync
public class MasterConfig {
    @Bean
    public SecureRandom applicationSecureRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

    @Bean
    public MessageSource applicationMessageSource() {
        var result = new ResourceBundleMessageSource();
        result.setBasenames("messages","problem_messages");
        result.setDefaultEncoding("UTF-8");
        result.setDefaultLocale(Locale.ENGLISH);
        result.setFallbackToSystemLocale(true);
        result.setUseCodeAsDefaultMessage(true);
        return result;
    }
}
