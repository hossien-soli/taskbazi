package dev.hspl.taskbazi.user.infrastructure.component;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.UserEmailAddressResolverAPI;
import dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// uses a database repository(jpa repository) directly(without caching) for resolving the email address of user
// potential implementations: CachingUserEmailAddressResolver

@Component
@RequiredArgsConstructor
public class PersistenceUserEmailAddressResolverAPI implements UserEmailAddressResolverAPI {
    private final UserJPARepository jpaRepository;

    @Override
    public Optional<EmailAddress> resolveOne(UserRole targetRole, UserId userId) {
        Optional<String> fetchResult = jpaRepository.getEmailAddressByIdAndRoleMatch(
                userId.value(),
                targetRole
        );

        return fetchResult.map(EmailAddress::new);
    }

    @Override
    public Map<UserId, EmailAddress> resolveMany(UserRole targetRole, Collection<UserId> userIds) {
        return jpaRepository.getUserIdWithEmailAddressByIdsAndRoleMatch(
                userIds.stream().map(UserId::value).toList(),
                targetRole
        ).stream().collect(Collectors.toMap(
                record -> new UserId(record.userId()),
                record -> new EmailAddress(record.emailAddress())
        ));
    }
}
