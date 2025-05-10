package dev.hspl.taskbazi.user.domain.service;

// potential implementations: DatabaseUserAuthenticationConstraints, ConfigFileUserAuthenticationConstraints
// or ConfigServerUserAuthenticationConstraints

public interface UserAuthenticationConstraints {
    int registrationSessionLimitationDelaySeconds(); // delay enforced between each client-registration-session
    int registrationSessionLifetimeSeconds(); // client-registration-session lifetime seconds
    short registrationSessionMaxAllowedAttempts();
//    short refreshTokenSizeInRandomBytes();

    short refreshTokenLifetimeHours();
    short accessTokenLifetimeMinutes();

    short maxAllowedActiveLoginSessions(); // max allowed active login sessions of user
}
