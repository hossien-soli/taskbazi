package dev.hspl.taskbazi.user.application.init;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.user.domain.service.UserUniquenessChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryUserUniquenessChecker implements UserUniquenessChecker {
    private final UserRepository userRepository;

    @Override
    public boolean checkEmailAddressIsUnique(EmailAddress emailAddress, UserRole userRole) {
        return !userRepository.existsByEmail(emailAddress, userRole);
    }

    @Override
    public boolean checkUsernameIsUnique(Username username, UserRole userRole) {
        return !userRepository.existsByUsername(username, userRole);
    }
}
