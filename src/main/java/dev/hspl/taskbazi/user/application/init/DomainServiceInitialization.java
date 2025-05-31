package dev.hspl.taskbazi.user.application.init;

import dev.hspl.taskbazi.user.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainServiceInitialization {
    @Bean
    public ClientRegistrationService clientRegistrationServiceSingleton(
            PasswordProtector passwordProtector,
            VerificationCodeProtector verificationCodeProtector,
            UserUniquenessChecker userUniquenessChecker,
            ClientRegistrationEmailSender clientRegistrationEmailSender,
            UserAuthenticationConstraints userAuthenticationConstraints
    ) {
        return new ClientRegistrationService(
                passwordProtector,
                verificationCodeProtector,
                userUniquenessChecker,
                clientRegistrationEmailSender,
                userAuthenticationConstraints
        );
    }

    @Bean
    public UserLoginService userLoginServiceSingleton(
            PasswordProtector passwordProtector,
            UserAuthenticationConstraints userAuthenticationConstraints,
            OpaqueTokenProtector opaqueTokenProtector,
            AccessTokenService accessTokenService
    ) {
        return new UserLoginService(
                passwordProtector,
                userAuthenticationConstraints,
                opaqueTokenProtector,
                accessTokenService
        );
    }
}
