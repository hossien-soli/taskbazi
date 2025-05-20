package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.user.domain.service.UserAuthenticationConstraints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// potential implementations: DatabaseUserAuthenticationConstraints, ConfigFileUserAuthenticationConstraints
// or ConfigServerUserAuthenticationConstraints

@Component
public class ConfigFileUserAuthenticationConstraints implements UserAuthenticationConstraints {
    @Value("${custom.domain.user.registration.session_limitation_delay}")
    private int registrationSessionLimitationDelaySeconds;

    @Value("${custom.domain.user.registration.session_lifetime}")
    private int registrationSessionLifetimeSeconds;

    @Value("${custom.domain.user.registration.session_max_allowed_attempts}")
    private short registrationSessionMaxAllowedAttempts;

//    @Value("${custom.domain.user.login.refresh_token_size_in_random_bytes}")
//    private short refreshTokenSize; // number of random bytes

    @Value("${custom.domain.user.login.refresh_token_lifetime_hours}")
    private short refreshTokenLifetimeHours;

    @Value("${custom.domain.user.login.access_token_lifetime_minutes}")
    private short accessTokenLifetimeMinutes;

    @Value("${custom.domain.user.login.max_allowed_active_login_sessions}")
    private short maxAllowedActiveLoginSessions;

    @Value("${custom.domain.user.login.token_refresh_delay_seconds}")
    private int tokenRefreshDelaySeconds;

    @Override
    public int registrationSessionLimitationDelaySeconds() {
        return registrationSessionLimitationDelaySeconds;
    }

    @Override
    public int registrationSessionLifetimeSeconds() {
        return registrationSessionLifetimeSeconds;
    }

    @Override
    public short registrationSessionMaxAllowedAttempts() {
        return registrationSessionMaxAllowedAttempts;
    }

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

    @Override
    public int tokenRefreshDelaySeconds() {
        return tokenRefreshDelaySeconds;
    }
}
