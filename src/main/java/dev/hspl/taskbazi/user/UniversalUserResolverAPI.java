package dev.hspl.taskbazi.user;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.common.domain.value.UserRole;

import java.util.Optional;

public interface UniversalUserResolverAPI {
    Optional<? extends UniversalUser> resolveOne(UserRole targetRole, UserId userId);
}
