package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.UniversalUserResolverAPI;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DomainRepositoryUniversalUserResolverAPI implements UniversalUserResolverAPI {
    private final UserRepository domainRepository;

    @Override
    public Optional<? extends UniversalUser> resolveOne(UserRole targetRole, UserId userId) {
        return domainRepository.find(userId,targetRole);
    }
}
