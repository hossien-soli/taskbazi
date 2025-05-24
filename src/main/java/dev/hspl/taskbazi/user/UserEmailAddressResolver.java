package dev.hspl.taskbazi.user;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

// this is an API for the outside world(outside user module!!!)

public interface UserEmailAddressResolver {
    Optional<EmailAddress> resolveOne(UserRole targetRole, UserId userId);

    Map<UserId, EmailAddress> resolveMany(UserRole targetRole, Collection<UserId> userIds);
}
