package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.domain.service.UserAuthenticationConstraints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// potential implementations: DatabaseUserAuthenticationConstraints, ConfigFileUserAuthenticationConstraints
// or ConfigServerUserAuthenticationConstraints

@Component
public class ConfigFileUserAuthenticationConstraints implements UserAuthenticationConstraints {
    @Value("${custom.domain.user.registration.session_limitation_delay}")
    private int limitationDelaySeconds;

    @Value("${custom.domain.user.registration.session_lifetime}")
    private int lifetimeSeconds;

    @Value("${custom.domain.user.registration.session_max_allowed_attempts}")
    private short maxAllowedAttempts;

//    @Value("${custom.domain.user.login.refresh_token_size_in_random_bytes}")
//    private short refreshTokenSize; // number of random bytes

    @Value("${custom.domain.user.login.refresh_token_lifetime_hours}")
    private short refreshTokenLifetimeHours;

    @Value("${custom.domain.user.login.access_token_lifetime_minutes}")
    private short accessTokenLifetimeMinutes;

    @Value("${custom.domain.user.login.max_allowed_active_login_sessions}")
    private short maxAllowedActiveLoginSessions;

    @Override
    public int registrationSessionLimitationDelaySeconds() {
        return limitationDelaySeconds;
    }

    @Override
    public int registrationSessionLifetimeSeconds() {
        return lifetimeSeconds;
    }

    @Override
    public short registrationSessionMaxAllowedAttempts() {
        return maxAllowedAttempts;
    }

//    @Override
//    public short refreshTokenSizeInRandomBytes() {
//        return refreshTokenSize;
//    }

    @Override
    public short refreshTokenLifetimeHours() {
        return refreshTokenLifetimeHours;
    }

    @Override
    public short accessTokenLifetimeMinutes() {
        return accessTokenLifetimeMinutes;
    }

    @Override
    public short maxAllowedActiveLoginSessions() {
        return maxAllowedActiveLoginSessions;
    }
}
