package dev.hspl.taskbazi.user.infrastructure.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.hspl.taskbazi.common.domain.value.*;
import dev.hspl.taskbazi.user.domain.exception.InvalidAccessTokenException;
import dev.hspl.taskbazi.user.domain.service.AccessTokenService;
import dev.hspl.taskbazi.user.domain.value.AccessToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTAccessTokenService implements AccessTokenService {
    @Value("${custom.infrastructure.jwt.signature_secret}")
    private String jwtSignatureSecret;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    void init() {
        this.algorithm = Algorithm.HMAC384(jwtSignatureSecret);
        this.verifier = JWT.require(algorithm).build();
    }

    private static final String CLAIM_USER_FULL_NAME = "u_fn";
    private static final String CLAIM_USER_EMAIL = "u_em";
    private static final String CLAIM_USER_USERNAME = "u_un";
    private static final String CLAIM_USER_STATUS = "u_s";
    private static final String CLAIM_USER_ROLE = "u_r";

    @Override
    public AccessToken generateTokenForUser(UniversalUser universalUser, short accessTokenLifetimeMinutes) {
        String plainJWTToken = JWT.create()
                .withSubject(universalUser.universalUserId().value().toString())
                //.withIssuer("TASKBAZI")
                //.withAudience("TASKBAZI")
                .withClaim(CLAIM_USER_FULL_NAME, universalUser.universalUserFullName())
                .withClaim(CLAIM_USER_EMAIL, universalUser.universalUserEmailAddress().value())
                .withClaim(CLAIM_USER_USERNAME, universalUser.universalUserUsername().value())
                .withClaim(CLAIM_USER_STATUS, universalUser.isAccountActive())
                .withClaim(CLAIM_USER_ROLE, universalUser.userRole().toString())
                .withExpiresAt(Instant.now().plusSeconds(accessTokenLifetimeMinutes * 60))
                .sign(algorithm);

        return new AccessToken(plainJWTToken);
    }

    @Override
    public UniversalUser validateTokenAndExtractUserInfo(AccessToken token) throws InvalidAccessTokenException {
        try {
            DecodedJWT jwt = verifier.verify(token.value());

            return new JWTPayloadUniversalUser(
                    jwt.getSubject(),
                    jwt.getClaim(CLAIM_USER_FULL_NAME).asString(),
                    jwt.getClaim(CLAIM_USER_EMAIL).asString(),
                    jwt.getClaim(CLAIM_USER_USERNAME).asString(),
                    jwt.getClaim(CLAIM_USER_STATUS).asBoolean(),
                    jwt.getClaim(CLAIM_USER_ROLE).asString()
            );
        } catch (JWTVerificationException exception) {
            throw new InvalidAccessTokenException();
        }
    }

    static class JWTPayloadUniversalUser implements UniversalUser {
        private final String userId;
        private final String userFullName;
        private final String userEmail;
        private final String userUsername;
        private final boolean userStatus;
        private final String userRole;

        public JWTPayloadUniversalUser(String userId, String userFullName, String userEmail,
                                       String userUsername, boolean userStatus, String userRole) {
            this.userId = userId;
            this.userFullName = userFullName;
            this.userEmail = userEmail;
            this.userUsername = userUsername;
            this.userStatus = userStatus;
            this.userRole = userRole;
        }

        @Override
        public UserId universalUserId() {
            return new UserId(UUID.fromString(userId));
        }

        @Override
        public String universalUserFullName() {
            return userFullName;
        }

        @Override
        public EmailAddress universalUserEmailAddress() {
            return new EmailAddress(userEmail);
        }

        @Override
        public Username universalUserUsername() {
            return new Username(userUsername);
        }

        @Override
        public boolean isAccountActive() {
            return userStatus;
        }

        @Override
        public UserRole userRole() {
            return UserRole.valueOf(userRole);
        }
    }
}
