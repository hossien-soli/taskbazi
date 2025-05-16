package dev.hspl.taskbazi.user.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import dev.hspl.taskbazi.user.domain.entity.LoginSession;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
import dev.hspl.taskbazi.user.domain.entity.User;
import dev.hspl.taskbazi.user.domain.value.*;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.ClientRegistrationSessionJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.LoginSessionJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.RefreshTokenJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

// map domain stuff(entities) to persistence(jpa) stuff(entities)

@Component
@RequiredArgsConstructor
public class UserModulePersistenceMapper {
    private final ObjectMapper objectMapper;

    public ClientRegistrationSessionJPAEntity mapClientRegistrationSessionToJPAEntity(
            ClientRegistrationSession domainSession
    ) {
        ClientRegistrationSessionJPAEntity result = new ClientRegistrationSessionJPAEntity();
        result.setId(domainSession.getId());
        result.setEmailAddress(domainSession.getEmailAddress().value());
        result.setClientFullName(domainSession.getClientFullName().value());
        result.setClientUsername(domainSession.getClientUsername().value());
        result.setClientHashedPassword(domainSession.getClientPassword().value());
        result.setHashedVerificationCode(domainSession.getVerificationCode().value());
        result.setRequestClientIdentifier(domainSession.getRequestClientIdentifier().value());
        result.setVerificationAttempts(domainSession.getVerificationAttempts());
        result.setBlocked(domainSession.isBlocked());
        result.setVerified(domainSession.isVerified());
        result.setRegistered(domainSession.isRegistered());
        result.setExpired(domainSession.isExpired());
        result.setCreatedAt(domainSession.getCreatedAt());
        result.setClosedAt(domainSession.getClosedAt());
        result.setVersion(domainSession.getVersion());
        return result;
    }

    public ClientRegistrationSession mapJPAEntityToClientRegistrationSession(
            ClientRegistrationSessionJPAEntity jpaSession
    ) {
        return ClientRegistrationSession.existingSession(
                jpaSession.getId(),
                new EmailAddress(jpaSession.getEmailAddress()),
                new UserFullName(jpaSession.getClientFullName()),
                new Username(jpaSession.getClientUsername()),
                new ProtectedPassword(jpaSession.getClientHashedPassword()),
                new ProtectedVerificationCode(jpaSession.getHashedVerificationCode()),
                new RequestClientIdentifier(jpaSession.getRequestClientIdentifier()),
                jpaSession.getVerificationAttempts(),
                jpaSession.isBlocked(),
                jpaSession.isVerified(),
                jpaSession.isRegistered(),
                jpaSession.isExpired(),
                jpaSession.getCreatedAt(),
                jpaSession.getClosedAt(),
                jpaSession.getVersion()
        );
    }

    public UserJPAEntity mapUserToJPAEntity(User user) {
        UserJPAEntity result = new UserJPAEntity();
        result.setId(user.getId().value());
        result.setFullName(user.getFullName().value());
        result.setEmailAddress(user.getEmailAddress().value());
        result.setUsername(user.getUsername().value());
        result.setHashedPassword(user.getPassword().value());
        result.setRole(user.getRole());
        result.setBanned(user.isBanned());
        result.setRegisteredAt(user.getRegisteredAt());
        result.setVersion(user.getVersion());
        return result;
    }

    public User mapJPAEntityToUser(UserJPAEntity jpaEntity) {
        return User.existingUser(
                new UserId(jpaEntity.getId()),
                new UserFullName(jpaEntity.getFullName()),
                new EmailAddress(jpaEntity.getEmailAddress()),
                new Username(jpaEntity.getUsername()),
                new ProtectedPassword(jpaEntity.getHashedPassword()),
                jpaEntity.isBanned(),
                jpaEntity.getRegisteredAt(),
                jpaEntity.getRole(),
                jpaEntity.getVersion()
        );
    }

    private String mapRequestIdentificationDetailsToJSONString(
            @Nullable RequestIdentificationDetails identificationDetails
    ) {
        if (identificationDetails == null) return null;

        try {
            return objectMapper.writeValueAsString(identificationDetails);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    private RequestIdentificationDetails mapJSONStringToRequestIdentificationDetails(
            @Nullable String jsonString
    ) {
        if (jsonString == null || jsonString.isEmpty()) return null;

        try {
            return objectMapper.readValue(jsonString, RequestIdentificationDetails.class);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public LoginSession mapJPAEntityToLoginSession(LoginSessionJPAEntity jpaEntity) {
        return LoginSession.existingSession(
                jpaEntity.getId(),
                new UserId(jpaEntity.getUserId()),
                jpaEntity.getNumberOfTokenRefresh(),
                jpaEntity.getState(),
                new RequestClientIdentifier(jpaEntity.getRequestClientIdentifier()),
                mapJSONStringToRequestIdentificationDetails(jpaEntity.getRequestIdentificationDetails()),
                jpaEntity.getCreatedAt(),
                jpaEntity.getStateUpdatedAt(),
                jpaEntity.getVersion()
        );
    }

    public LoginSessionJPAEntity mapLoginSessionToJPAEntity(LoginSession loginSession) {
        LoginSessionJPAEntity result = new LoginSessionJPAEntity();
        result.setId(loginSession.getId());
        result.setUserId(loginSession.getUserId().value());
        result.setNumberOfTokenRefresh(loginSession.getNumberOfTokenRefresh());
        result.setState(loginSession.getState());
        result.setRequestClientIdentifier(loginSession.getRequestClientIdentifier().value());
        result.setRequestIdentificationDetails(mapRequestIdentificationDetailsToJSONString(
                loginSession.getRequestIdentificationDetails()
        ));
        result.setCreatedAt(loginSession.getCreatedAt());
        result.setStateUpdatedAt(loginSession.getStateUpdatedAt());
        result.setVersion(loginSession.getVersion());
        return result;
    }

    public RefreshToken mapJPAEntityToRefreshToken(RefreshTokenJPAEntity jpaEntity) {
        LoginSessionJPAEntity loginSession = jpaEntity.getLoginSession();

        return RefreshToken.existingInstance(
                jpaEntity.getId(),
                new ProtectedOpaqueToken(jpaEntity.getHashedActualToken()),
                jpaEntity.getLifetimeHours(),
                jpaEntity.isRefreshed(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getRefreshedAt(),
                jpaEntity.getVersion(),
                loginSession.getId(),
                new UserId(loginSession.getUserId()),
                loginSession.getNumberOfTokenRefresh(),
                loginSession.getState(),
                new RequestClientIdentifier(loginSession.getRequestClientIdentifier()),
                mapJSONStringToRequestIdentificationDetails(loginSession.getRequestIdentificationDetails()),
                loginSession.getCreatedAt(),
                loginSession.getStateUpdatedAt(),
                loginSession.getVersion()
        );
    }

    public RefreshTokenJPAEntity mapRefreshTokenToJPAEntity(RefreshToken refreshToken) {
        RefreshTokenJPAEntity result = new RefreshTokenJPAEntity();
        result.setId(refreshToken.getId());
        result.setHashedActualToken(refreshToken.getActualToken().value());
        result.setLifetimeHours(refreshToken.getLifetimeHours());
        result.setRefreshed(refreshToken.isRefreshed());
        result.setCreatedAt(refreshToken.getCreatedAt());
        result.setRefreshedAt(refreshToken.getRefreshedAt());
        result.setLoginSession(mapLoginSessionToJPAEntity(refreshToken.getLoginSession()));
        result.setVersion(refreshToken.getVersion());
        return result;
    }
}
