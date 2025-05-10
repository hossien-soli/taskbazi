package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.application.service.VerificationCodeGenerator;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class SecureRandomVerificationCodeGenerator implements VerificationCodeGenerator {
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom applicationSecureRandom;

    @Override
    public PlainVerificationCode generateNew() {
        StringBuilder sb = new StringBuilder(PlainVerificationCode.LENGTH);
        for (int i = 0; i < PlainVerificationCode.LENGTH; i++) {
            int index = applicationSecureRandom.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(index));
        }

        return new PlainVerificationCode(sb.toString());
    }
}
