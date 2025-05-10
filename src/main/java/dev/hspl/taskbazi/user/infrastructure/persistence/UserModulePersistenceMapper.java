package dev.hspl.taskbazi.user.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.*;
import dev.hspl.taskbazi.user.domain.entity.Client;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import dev.hspl.taskbazi.user.domain.entity.LoginSession;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
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
        result.setClientFullName(domainSession.getUserFullName().value());
        result.setUserUsername(domainSession.getUserUsername().value());
        result.setUserHashedPassword(domainSession.getUserPassword().value());
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
                new ClientFullName(jpaSession.getClientFullName()),
                new Username(jpaSession.getUserUsername()),
                new ProtectedPassword(jpaSession.getUserHashedPassword()),
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

    public UserJPAEntity mapClientToUserJPAEntity(Client client) {
        UserJPAEntity result = new UserJPAEntity();
        result.setId(client.getId().value());
        result.setFullName(client.getFullName().value());
        result.setEmailAddress(client.getEmailAddress().value());
        result.setUsername(client.getUsername().value());
        result.setHashedPassword(client.getPassword().value());
        result.setRole(UserRole.CLIENT);
        result.setBanned(client.isBanned());
        result.setRegisteredAt(client.getRegisteredAt());
        result.setVersion(client.getVersion());
        return result;
    }

    public Client mapUserJPAEntityToClient(UserJPAEntity jpaUser) {
        return Client.existingClient(
                new UserId(jpaUser.getId()),
                new ClientFullName(jpaUser.getFullName()),
                new EmailAddress(jpaUser.getEmailAddress()),
                new Username(jpaUser.getUsername()),
                new ProtectedPassword(jpaUser.getHashedPassword()),
                jpaUser.isBanned(),
                jpaUser.getRegisteredAt(),
                jpaUser.getVersion()
        );
    }

    private String mapRequestIdentificationDetailsToJSONString(
            @Nullable RequestIdentificationDetails identificationDetails
    ) {
        if (identificationDetails == null) return null;

        try {
            return objectMapper.writeValueAsString(identificationDetails);
        } catch (JsonProcessingException exception) { return null; }
    }

    private RequestIdentificationDetails mapJSONStringToRequestIdentificationDetails(
            @Nullable String jsonString
    ) {
        if (jsonString == null || jsonString.isEmpty()) return null;

        try {
            return objectMapper.readValue(jsonString,RequestIdentificationDetails.class);
        } catch (JsonProcessingException exception) { return null; }
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
