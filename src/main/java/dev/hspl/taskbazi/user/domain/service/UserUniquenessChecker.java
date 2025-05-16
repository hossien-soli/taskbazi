package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;

public interface UserUniquenessChecker {
    boolean checkEmailAddressIsUnique(
            EmailAddress emailAddress,
            UserRole userRole
    );

    boolean checkUsernameIsUnique(
            Username username,
            UserRole userRole
    );
}
