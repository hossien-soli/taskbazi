package dev.hspl.taskbazi.user.application.service;

import dev.hspl.taskbazi.common.application.GlobalDomainEventPublisher;
import dev.hspl.taskbazi.common.application.TimeProvider;
import dev.hspl.taskbazi.common.application.UUIDGenerator;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.user.application.exception.InvalidRegistrationSessionIdException;
import dev.hspl.taskbazi.user.application.exception.PasswordConfirmationMismatchException;
import dev.hspl.taskbazi.user.application.usage.ClientRegistrationFinalizeUseCase;
import dev.hspl.taskbazi.user.application.usage.ClientRegistrationRequestUseCase;
import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationFinalizeCommand;
import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationRequestCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationFinalizeResult;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationRequestResult;
import dev.hspl.taskbazi.user.domain.entity.ClientRegistrationSession;
import dev.hspl.taskbazi.user.domain.entity.User;
import dev.hspl.taskbazi.user.domain.repository.ClientRegistrationSessionRepository;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.user.domain.service.ClientRegistrationService;
import dev.hspl.taskbazi.user.domain.service.UserAuthenticationConstraints;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.RegistrationVerificationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientRegistrationApplicationService implements ClientRegistrationRequestUseCase, ClientRegistrationFinalizeUseCase {
    private final TimeProvider timeProvider;
    private final UUIDGenerator uuidGenerator;
    private final ClientRegistrationService domainService;
    private final ClientRegistrationSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final VerificationCodeGenerator verificationCodeGenerator;
    private final GlobalDomainEventPublisher domainEventPublisher;

    @Override
    public ClientRegistrationRequestResult execute(
            ClientRegistrationRequestCommand command,
            @NonNull RequestClientIdentifier requestClientIdentifier
    ) {
        PlainPassword password = command.password();
        PlainPassword passwordConfirmation = command.passwordConfirmation();
        boolean matches = password.equals(passwordConfirmation);
        if (!matches) {
            throw new PasswordConfirmationMismatchException();
        }

        EmailAddress emailAddress = command.emailAddress();

        LocalDateTime lastSessionCreationTime = sessionRepository.getLastSessionByEmailOrRequestClientIdentifier(
                emailAddress,
                requestClientIdentifier
        );

        LocalDateTime currentDateTime = timeProvider.currentDateTime();
        UUID newSessionId = uuidGenerator.generateNew();
        PlainVerificationCode plainVerificationCode = verificationCodeGenerator.generateNew();

        ClientRegistrationSession registrationSession = domainService.createNewSession(
                currentDateTime,
                newSessionId,
                emailAddress,
                command.fullName(),
                command.username(),
                password,
                plainVerificationCode,
                requestClientIdentifier,
                lastSessionCreationTime
        );

        sessionRepository.saveSession(registrationSession);

        UserAuthenticationConstraints constraints = domainService.getConstraints();
        return new ClientRegistrationRequestResult(
                registrationSession.getId(),
                constraints.registrationSessionLifetimeSeconds(),
                constraints.registrationSessionLimitationDelaySeconds(),
                constraints.registrationSessionMaxAllowedAttempts()
        );
    }

    @Override
    public ClientRegistrationFinalizeResult execute(
            ClientRegistrationFinalizeCommand command,
            @NonNull RequestClientIdentifier requestClientIdentifier
    ) {
        UUID sessionId = command.registrationSessionId();
        PlainVerificationCode userVerificationCode = command.verificationCode();

        Optional<ClientRegistrationSession> fetchResult = sessionRepository.findSessionById(sessionId);
        ClientRegistrationSession session = fetchResult.orElseThrow(InvalidRegistrationSessionIdException::new);

        LocalDateTime currentDateTime = timeProvider.currentDateTime();

        RegistrationVerificationResult result = session.tryVerify(
                currentDateTime,
                userVerificationCode,
                requestClientIdentifier,
                domainService.getVerificationCodeProtector(),
                domainService.getConstraints()
        );

        boolean success = result.equals(RegistrationVerificationResult.SUCCESS);
        boolean userRegistered = false;
        if (success) {
            UserId newUserId = new UserId(uuidGenerator.generateNew());

            User client = domainService.registerClient(currentDateTime, newUserId, session);
            userRepository.save(client);
            domainEventPublisher.publishAll(client);

            userRegistered = true;
        }

        sessionRepository.saveSession(session); // actually with jpa we don't need this!!!

        return new ClientRegistrationFinalizeResult(userRegistered, result);
    }
}
