package dev.hspl.taskbazi.user.presentation.web;

import dev.hspl.taskbazi.common.domain.value.RequestClientIdentifier;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.presentation.web.ControllerUtils;
import dev.hspl.taskbazi.common.presentation.web.component.HttpRemoteAddressResolver;
import dev.hspl.taskbazi.common.presentation.web.component.RequestIdentificationDetailsResolver;
import dev.hspl.taskbazi.user.application.dto.PresentedRefreshToken;
import dev.hspl.taskbazi.user.application.usage.TokenRotationUseCase;
import dev.hspl.taskbazi.user.application.usage.UserLoginUseCase;
import dev.hspl.taskbazi.user.application.usage.cmd.TokenRotationCommand;
import dev.hspl.taskbazi.user.application.usage.cmd.UserLoginCommand;
import dev.hspl.taskbazi.user.application.usage.result.TokenRotationResult;
import dev.hspl.taskbazi.user.application.usage.result.UserLoginResult;
import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.UsernameOrEmailAddress;
import dev.hspl.taskbazi.user.presentation.web.dto.LoginHttpRequest;
import dev.hspl.taskbazi.user.presentation.web.dto.TokenRotationHttpRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// all type of users (not specific to clients)

@RestController
@RequiredArgsConstructor
public class UserLoginController extends ControllerUtils {
    private final UserLoginUseCase loginUseCase;
    private final TokenRotationUseCase tokenRotationUseCase;
    private final HttpRemoteAddressResolver addressResolver;
    private final RequestIdentificationDetailsResolver requestIDDetailsResolver;

    @PostMapping("/client/login/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> translateLoginUseCase(
            HttpServletRequest request,
            @RequestBody LoginHttpRequest payload
    ) {
        var command = new UserLoginCommand(
                UserRole.CLIENT,
                new UsernameOrEmailAddress(payload.usernameOrEmailAddress()),
                new PlainPassword(payload.password()),
                new RequestClientIdentifier(addressResolver.resolve(request)),
                requestIDDetailsResolver.resolve(request)
        );

        UserLoginResult result = loginUseCase.execute(command);

        return Map.of(
                "refreshToken", result.refreshTokenForUser().getRawTokenForUser(),
                "accessToken", result.accessToken().value(),
                "linkedUserInfo", Map.of(
                        "userId", result.userInfo().universalUserId().value(),
                        "fullName", result.userInfo().universalUserFullName(),
                        "emailAddress", result.userInfo().universalUserEmailAddress().value(),
                        "username", result.userInfo().universalUserUsername().value(),
                        "isAccountActive", result.userInfo().isAccountActive(),
                        "role", UserRole.CLIENT
                )
        );
    }

    @PostMapping("/client/login/follow")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> translateTokenRotationUseCase(
            HttpServletRequest request,
            @RequestBody TokenRotationHttpRequest payload
    ) {
        PresentedRefreshToken refreshToken = PresentedRefreshToken.fromUserRawPresentedToken(payload.refreshToken());
        UserRole refreshTokenRole = refreshToken.getOrParseElements().userRole();
        if (!refreshTokenRole.equals(UserRole.CLIENT)) {
            throw new UserRoleMismatchTokenRotationException();
        }

        var command = new TokenRotationCommand(
                refreshToken,
                new RequestClientIdentifier(addressResolver.resolve(request)),
                requestIDDetailsResolver.resolve(request)
        );

        TokenRotationResult result = tokenRotationUseCase.execute(command);

        if (result.success()) {
            return Map.of(
                    "success", true,
                    "refreshResult", result.refreshResult(),
                    "newRefreshToken", result.newRefreshToken().getRawTokenForUser(),
                    "newAccessToken", result.newAccessToken().value()
            );
        }

        return Map.of(
                "success", false,
                "refreshResult", result.refreshResult()
        );
    }
}
