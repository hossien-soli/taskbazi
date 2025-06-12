package dev.hspl.taskbazi.user.presentation.web;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.common.presentation.web.ControllerUtils;
import dev.hspl.taskbazi.common.presentation.web.component.HttpRemoteAddressResolver;
import dev.hspl.taskbazi.user.application.usage.ClientRegistrationFinalizeUseCase;
import dev.hspl.taskbazi.user.application.usage.ClientRegistrationRequestUseCase;
import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationFinalizeCommand;
import dev.hspl.taskbazi.user.application.usage.cmd.ClientRegistrationRequestCommand;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationFinalizeResult;
import dev.hspl.taskbazi.user.application.usage.result.ClientRegistrationRequestResult;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.PlainVerificationCode;
import dev.hspl.taskbazi.user.domain.value.UserFullName;
import dev.hspl.taskbazi.user.presentation.web.dto.RegistrationFinalizeHttpRequest;
import dev.hspl.taskbazi.user.presentation.web.dto.RegistrationRequestHttpRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientRegistrationController extends ControllerUtils {
    private final ClientRegistrationRequestUseCase requestUseCase;
    private final ClientRegistrationFinalizeUseCase finalizeUseCase;
    private final HttpRemoteAddressResolver addressResolver;

    @PostMapping("/client/register/request")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> translateRequestUseCase(
            HttpServletRequest request,
            @RequestBody RegistrationRequestHttpRequest payload
    ) {
        var command = new ClientRegistrationRequestCommand(
                new UserFullName(payload.fullName()),
                new EmailAddress(payload.emailAddress()),
                new Username(payload.username()),
                new PlainPassword(payload.password()),
                new PlainPassword(payload.passwordConfirmation()),
                new RequestClientIdentifier(addressResolver.resolve(request))
        );

        ClientRegistrationRequestResult result = requestUseCase.execute(command);

        return Map.of(
                "registrationSessionId", result.registrationSessionId(),
                "sessionLifetimeSeconds", result.sessionLifetimeSeconds(),
                "sessionLimitationDelaySeconds", result.sessionLimitationDelaySeconds(),
                "maxAllowedAttempts", result.maxAllowedAttempts()
        );
    }

    @PostMapping("/client/register/finalize")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> translateFinalizeUseCase(
            HttpServletRequest request,
            @RequestBody RegistrationFinalizeHttpRequest payload
    ) {
        UUID registrationSessionId = validateUUIDString(payload.registrationSessionId());
        PlainVerificationCode verificationCode = new PlainVerificationCode(payload.verificationCode());

        var command = new ClientRegistrationFinalizeCommand(
                registrationSessionId,
                verificationCode,
                new RequestClientIdentifier(addressResolver.resolve(request))
        );

        ClientRegistrationFinalizeResult result = finalizeUseCase.execute(command);

        return Map.of(
                "userRegistered", result.userRegistered(),
                "verificationResult", result.verificationResult()
        );
    }
}
