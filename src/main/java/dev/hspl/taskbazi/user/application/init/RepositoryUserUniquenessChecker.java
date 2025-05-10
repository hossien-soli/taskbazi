package dev.hspl.taskbazi.user.application.init;

import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.user.domain.service.UserUniquenessChecker;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryUserUniquenessChecker implements UserUniquenessChecker {
    private final UserRepository userRepository;

    @Override
    public boolean checkEmailAddressIsUnique(EmailAddress emailAddress) {
        return !userRepository.existsByEmail(emailAddress);
    }

    @Override
    public boolean checkUsernameIsUnique(Username username) {
        return !userRepository.existsByUsername(username);
    }
}
