package dev.hspl.taskbazi.user.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.GenericUser;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshTokenElements;
import dev.hspl.taskbazi.user.application.exception.InvalidUsernameOrEmailAddressLoginException;
import dev.hspl.taskbazi.user.application.usage.TokenRefreshUseCase;
import dev.hspl.taskbazi.user.application.usage.UserLoginUseCase;
import dev.hspl.taskbazi.user.application.usage.cmd.TokenRefreshCommand;
import dev.hspl.taskbazi.user.application.usage.cmd.UserLoginCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRefreshResult;
import dev.hspl.taskbazi.user.application.usage.result.UserLoginResult;
import dev.hspl.taskbazi.user.domain.entity.Client;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
import dev.hspl.taskbazi.user.domain.repository.RefreshTokenRepository;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.user.domain.service.UserLoginService;
import dev.hspl.taskbazi.user.domain.value.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginApplicationService implements UserLoginUseCase, TokenRefreshUseCase {
    private final TimeProvider timeProvider;
    private final UUIDGenerator uuidGenerator;
    private final UserLoginService domainService;
    private final UserRepository userRepository;
    private final OpaqueTokenGenerator opaqueTokenGenerator;
    private final RefreshTokenRepository tokenRepository;
    private final GlobalDomainEventPublisher domainEventPublisher;

    @Override
    public UserLoginResult execute(
            UserLoginCommand command,
            UserRole roleToLogin,
            @NonNull RequestClientIdentifier requestClientIdentifier,
            @Nullable RequestIdentificationDetails requestIdentificationDetails
    ) {
        UsernameOrEmailAddress usernameOrEmailAddress = command.usernameOrEmailAddress();
        boolean isEmailAddress = usernameOrEmailAddress.isEmailAddress();

        GenericUser genericUserInfo = null;
        AuthenticatableUser authenticatableUser = null;
        short numberOfUserActiveLoginSessions = Short.MAX_VALUE;

        if (roleToLogin.equals(UserRole.CLIENT)) {
            Optional<Client> fetchResult = isEmailAddress ?
                    userRepository.findClientByEmailAddress(usernameOrEmailAddress.asEmailAddress()) :
                    userRepository.findClientByUsername(usernameOrEmailAddress.asUsername());

            Client client = fetchResult.orElseThrow(InvalidUsernameOrEmailAddressLoginException::new);
            genericUserInfo = client;
            authenticatableUser = client;

            numberOfUserActiveLoginSessions = tokenRepository.numberOfClientActiveLoginSessions(genericUserInfo.genericUserId());
        } else {
            throw new UnsupportedOperationException("non-client user login logic not implemented yet!");
        }

        LocalDateTime currentDateTime = timeProvider.currentDateTime();

        UUID newRefreshTokenId = uuidGenerator.generateNew();
        UUID newLoginSessionId = uuidGenerator.generateNew();

        PlainOpaqueToken plainActualRefreshToken = opaqueTokenGenerator.generateNew(45);

        LoginSessionTrackingInfo result = domainService.loginUser(
                currentDateTime,
                authenticatableUser,
                genericUserInfo,
                command.password(),
                numberOfUserActiveLoginSessions,
                newRefreshTokenId,
                newLoginSessionId,
                requestClientIdentifier,
                requestIdentificationDetails,
                plainActualRefreshToken
        );

        RefreshToken resultRefreshToken = result.refreshToken();
        if (roleToLogin.equals(UserRole.CLIENT)) {
            tokenRepository.saveForClient(resultRefreshToken);
            domainEventPublisher.publishAll(resultRefreshToken);
        } else {
            throw new UnsupportedOperationException("non-client user login logic not implemented yet!");
        }

        PresentedRefreshTokenElements elements = new PresentedRefreshTokenElements(
                roleToLogin,
                resultRefreshToken.getId(),
                plainActualRefreshToken
        );

        return new UserLoginResult(PresentedRefreshToken.buildForUser(elements),result.accessToken());
    }

    @Override
    public TokenRefreshResult execute(TokenRefreshCommand command) {
        PresentedRefreshTokenElements elements = command.presentedRefreshToken().getOrParseElements();

        return null;
    }
}
