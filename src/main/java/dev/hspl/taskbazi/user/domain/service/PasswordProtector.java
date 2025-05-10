package dev.hspl.taskbazi.user.domain.service;

import dev.hspl.taskbazi.user.domain.value.PlainPassword;
import dev.hspl.taskbazi.user.domain.value.ProtectedPassword;

// implemented in application
// The domain layer shouldn't be concerned with hashing at all — it's an implementation detail that belongs elsewhere
// However, this rule of hashing should be enforced within the business rules or domain layer
// So, I defined this contract

public interface PasswordProtector {
    ProtectedPassword protect(PlainPassword plainPassword);
    boolean matches(PlainPassword plainPassword, ProtectedPassword protectedPassword);
}
