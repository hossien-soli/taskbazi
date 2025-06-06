package dev.hspl.taskbazi.user.domain.repository;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.entity.User;

import java.util.Optional;

// implemented in infrastructure
// role property in some methods is needed when we want to store users in separate tables

public interface UserRepository {
    void save(User user);

    Optional<User> find(UserId id, UserRole role);

    Optional<User> findByUsername(Username username, UserRole role);

    Optional<User> findByEmailAddress(EmailAddress emailAddress, UserRole role);

    boolean existsByEmail(EmailAddress emailAddress, UserRole role);
    // since we are storing all roles in a shared table we should not include role in queries for existence check

    boolean existsByUsername(Username username, UserRole role);
    // since we are storing all roles in a shared table we should not include role in queries for existence check
}
