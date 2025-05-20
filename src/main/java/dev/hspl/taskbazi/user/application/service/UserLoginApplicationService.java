package dev.hspl.taskbazi.user.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshTokenElements;
import dev.hspl.taskbazi.user.application.exception.InvalidRefreshTokenIdException;
import dev.hspl.taskbazi.user.application.exception.InvalidUsernameOrEmailAddressLoginException;
import dev.hspl.taskbazi.user.application.exception.UserNotFoundLoginException;
import dev.hspl.taskbazi.user.application.usage.TokenRotationUseCase;
import dev.hspl.taskbazi.user.application.usage.UserLoginUseCase;
import dev.hspl.taskbazi.user.application.usage.cmd.TokenRotationCommand;
import dev.hspl.taskbazi.user.application.usage.cmd.UserLoginCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRotationResult;
import dev.hspl.taskbazi.user.application.usage.result.UserLoginResult;
import dev.hspl.taskbazi.user.domain.entity.LoginSession;
import dev.hspl.taskbazi.user.domain.entity.RefreshToken;
import dev.hspl.taskbazi.user.domain.entity.User;
import dev.hspl.taskbazi.user.domain.repository.RefreshTokenRepository;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.user.domain.service.UserLoginService;
import dev.hspl.taskbazi.user.domain.value.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginApplicationService implements UserLoginUseCase, TokenRotationUseCase {
    private final TimeProvider timeProvider;
    private final UUIDGenerator uuidGenerator;
    private final UserLoginService domainService;
    private final UserRepository userRepository;
    private final OpaqueTokenGenerator opaqueTokenGenerator;
    private final RefreshTokenRepository tokenRepository;
    private final GlobalDomainEventPublisher domainEventPublisher;

    private static final int REFRESH_TOKEN_SCALE = 45;

    @Override
    public UserLoginResult execute(UserLoginCommand command) {
        UserRole roleToLogin = command.roleToLogin();

        UsernameOrEmailAddress usernameOrEmailAddress = command.usernameOrEmailAddress();
        boolean isEmailAddress = usernameOrEmailAddress.isEmailAddress();

        Optional<User> fetchResult = isEmailAddress ?
                userRepository.findByEmailAddress(usernameOrEmailAddress.asEmailAddress(), roleToLogin) :
                userRepository.findByUsername(usernameOrEmailAddress.asUsername(), roleToLogin);

        User user = fetchResult.orElseThrow(InvalidUsernameOrEmailAddressLoginException::new);

        short numberOfUserActiveLoginSessions = tokenRepository.numberOfUserActiveLoginSessions(user.getId(), roleToLogin);

        LocalDateTime currentDateTime = timeProvider.currentDateTime();

        UUID newRefreshTokenId = uuidGenerator.generateNew();
        UUID newLoginSessionId = uuidGenerator.generateNew();

        PlainOpaqueToken plainActualRefreshToken = opaqueTokenGenerator.generateNew(REFRESH_TOKEN_SCALE);

        LoginSessionTrackingInfo result = domainService.loginUser(
                currentDateTime,
                roleToLogin,
                user,
                command.password(),
                numberOfUserActiveLoginSessions,
                newRefreshTokenId,
                newLoginSessionId,
                command.requestClientIdentifier(),
                command.requestIdentificationDetails(),
                plainActualRefreshToken
        );

        RefreshToken resultRefreshToken = result.refreshToken();
        tokenRepository.save(resultRefreshToken, roleToLogin);
        domainEventPublisher.publishAll(resultRefreshToken);

        PresentedRefreshTokenElements elements = new PresentedRefreshTokenElements(
                roleToLogin,
                resultRefreshToken.getId(),
                plainActualRefreshToken
        );

        return new UserLoginResult(PresentedRefreshToken.buildForUser(elements), result.accessToken());
    }

    @Override
    public TokenRotationResult execute(TokenRotationCommand command) {
        PresentedRefreshTokenElements elements = command.presentedRefreshToken().getOrParseElements();
        UserRole userRole = elements.userRole();
        UUID refreshTokenId = elements.refreshTokenId();
        PlainOpaqueToken userPlainActualRefreshToken = elements.plainActualRefreshToken();

        Optional<RefreshToken> fetchResult = tokenRepository.find(refreshTokenId, userRole);
        RefreshToken tokenToRefresh = fetchResult.orElseThrow(InvalidRefreshTokenIdException::new);

        LoginSession loginSession = tokenToRefresh.getLoginSession();
        UserId relatedUserId = loginSession.getUserId();

        Optional<User> userFetchResult = userRepository.find(relatedUserId, userRole);
        User relatedUser = userFetchResult.orElseThrow(UserNotFoundLoginException::new);

        LocalDateTime currentDateTime = timeProvider.currentDateTime();
        UUID newRefreshTokenId = uuidGenerator.generateNew();
        PlainOpaqueToken newPlainActualRefreshToken = opaqueTokenGenerator.generateNew(REFRESH_TOKEN_SCALE);

        LoginSessionExtensionResult domainResult = domainService.refreshAndRotateTheToken(
                currentDateTime,
                userRole,
                relatedUser,
                tokenToRefresh,
                userPlainActualRefreshToken,
                newRefreshTokenId,
                command.requestClientIdentifier(),
                command.requestIdentificationDetails(),
                newPlainActualRefreshToken
        );

        tokenRepository.save(tokenToRefresh, userRole); // actually with jpa we don't need this
        domainEventPublisher.publishAll(tokenToRefresh);

        TokenRefreshResult refreshResult = domainResult.refreshResult();
        TokenRotationResult finalResult = new TokenRotationResult(
                false, refreshResult, null, null
        );

        if (refreshResult.equals(TokenRefreshResult.SUCCESS)) {
            LoginSessionTrackingInfo trackingInfo = domainResult.trackingInfo();

            RefreshToken newRefreshToken = trackingInfo.refreshToken();
            tokenRepository.save(newRefreshToken, userRole);
            domainEventPublisher.publishAll(newRefreshToken);

            PresentedRefreshTokenElements newElements = new PresentedRefreshTokenElements(
                    userRole,
                    newRefreshTokenId,
                    newPlainActualRefreshToken
            );

            finalResult = new TokenRotationResult(
                    true,
                    refreshResult,
                    PresentedRefreshToken.buildForUser(newElements),
                    trackingInfo.accessToken()
            );
        }

        return finalResult;
    }
}
