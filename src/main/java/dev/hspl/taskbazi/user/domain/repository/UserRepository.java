package dev.hspl.taskbazi.user.domain.repository;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.entity.Client;
import dev.hspl.taskbazi.user.domain.value.UsernameOrEmailAddress;

import java.util.Optional;

// implemented in infrastructure
// Clients, Moderators, Admins

public interface UserRepository {
    void saveClient(Client client);

    Optional<Client> findClientByUsername(Username username);
    Optional<Client> findClientByEmailAddress(EmailAddress emailAddress);

    boolean existsByEmail(EmailAddress emailAddress);
    boolean existsByUsername(Username username);
}
