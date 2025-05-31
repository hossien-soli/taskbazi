package dev.hspl.taskbazi.common.application;

import dev.hspl.taskbazi.common.domain.value.UniversalUser;

public interface AuthenticatedUserProvider {
    UniversalUser currentAuthUser() throws AuthenticatedUserNotFoundException;
}
