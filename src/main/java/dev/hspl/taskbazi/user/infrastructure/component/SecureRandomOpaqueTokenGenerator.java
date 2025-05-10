package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.application.service.OpaqueTokenGenerator;
import dev.hspl.taskbazi.user.domain.value.PlainOpaqueToken;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SecureRandomOpaqueTokenGenerator implements OpaqueTokenGenerator {
    private final SecureRandom applicationSecureRandom;
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public SecureRandomOpaqueTokenGenerator(SecureRandom applicationSecureRandom) {
        this.applicationSecureRandom = applicationSecureRandom;
    }

    @Override
    public PlainOpaqueToken generateNew(int numberOfRandomBytes) {
        byte[] randomBytes = new byte[numberOfRandomBytes];
        applicationSecureRandom.nextBytes(randomBytes);
        String resultToken = base64Encoder.encodeToString(randomBytes);
        return new PlainOpaqueToken(resultToken);
    }
}
