package dev.hspl.taskbazi.user.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.domain.entity.Client;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.value.UsernameOrEmailAddress;
import dev.hspl.taskbazi.user.infrastructure.persistence.UserModulePersistenceMapper;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.UserJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SQLUserRepository implements UserRepository {
    private final UserJPARepository jpaRepository;
    private final UserModulePersistenceMapper mapper;

    @Override
    public void saveClient(Client client) {
        UserJPAEntity userRecord = mapper.mapClientToUserJPAEntity(client);
        jpaRepository.save(userRecord);
    }

    @Override
    public Optional<Client> findClientByUsername(Username username) {
        Optional<UserJPAEntity> fetchResult = jpaRepository.findRoleByUsername(
                UserRole.CLIENT,
                username.value()
        );

        return fetchResult.map(mapper::mapUserJPAEntityToClient);
    }

    @Override
    public Optional<Client> findClientByEmailAddress(EmailAddress emailAddress) {
        Optional<UserJPAEntity> fetchResult = jpaRepository.findRoleByEmailAddress(
                UserRole.CLIENT,
                emailAddress.value()
        );

        return fetchResult.map(mapper::mapUserJPAEntityToClient);
    }

    @Override
    public boolean existsByEmail(EmailAddress emailAddress) {
        return jpaRepository.existsByEmailAddress(emailAddress.value());
    }

    @Override
    public boolean existsByUsername(Username username) {
        return jpaRepository.existsByUsername(username.value());
    }
}
