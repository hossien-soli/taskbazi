package dev.hspl.taskbazi.user.infrastructure.persistence.repository;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.common.domain.value.Username;
import dev.hspl.taskbazi.user.domain.entity.User;
import dev.hspl.taskbazi.user.domain.repository.UserRepository;
import dev.hspl.taskbazi.user.infrastructure.persistence.UserModulePersistenceMapper;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.UserJPAEntity;
import dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// this repository manage all type/role of users in a single table

@Repository
@RequiredArgsConstructor
public class SQLUserRepository implements UserRepository {
    private final UserJPARepository jpaRepository;
    private final UserModulePersistenceMapper mapper;

    @Override
    public void save(User user) {
        UserJPAEntity userRecord = mapper.mapUserToJPAEntity(user);
        jpaRepository.save(userRecord);
    }

    @Override
    public Optional<User> findByUsername(Username username, UserRole userRole) {
        // apply role for more efficiency in application use-case and domain layer
        Optional<UserJPAEntity> fetchResult = jpaRepository.findRoleByUsername(
                userRole,
                username.value()
        );

        return fetchResult.map(mapper::mapJPAEntityToUser);
    }

    @Override
    public Optional<User> findByEmailAddress(EmailAddress emailAddress, UserRole userRole) {
        // apply role for more efficiency in application use-case and domain layer
        Optional<UserJPAEntity> fetchResult = jpaRepository.findRoleByEmailAddress(
                userRole,
                emailAddress.value()
        );

        return fetchResult.map(mapper::mapJPAEntityToUser);
    }

    @Override
    public boolean existsByEmail(EmailAddress emailAddress, UserRole userRole) {
        // we should ignore the role for this implementation because we have a unique constraint in database table for email
        return jpaRepository.existsByEmailAddress(emailAddress.value());
    }

    @Override
    public boolean existsByUsername(Username username, UserRole userRole) {
        // we should ignore the role for this implementation because we have a unique constraint in database table for username
        return jpaRepository.existsByUsername(username.value());
    }
}
