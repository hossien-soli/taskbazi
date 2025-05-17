package dev.hspl.taskbazi.user.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.common.domain.value.UserRole;
import dev.hspl.taskbazi.user.infrastructure.persistence.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

// repository for all type/role of users in a single table
// actually domain allow us to store different type/role of users inside separate tables

public interface UserJPARepository extends JpaRepository<UserJPAEntity, UUID> {
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.role = :roleToMatch")
    Optional<UserJPAEntity> findByIdAndRoleMatch(
            @Param("id") UUID id,
            @Param("roleToMatch") UserRole roleToMatch
    );

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role = :roleToMatch")
    Optional<UserJPAEntity> findByUsernameAndRoleMatch(
            @Param("username") String username,
            @Param("roleToMatch") UserRole roleToMatch
    );

    @Query("SELECT u FROM User u WHERE u.emailAddress = :emailAddress AND u.role = :roleToMatch")
    Optional<UserJPAEntity> findByEmailAddressAndRoleMatch(
            @Param("emailAddress") String emailAddress,
            @Param("roleToMatch") UserRole roleToMatch
    );

    boolean existsByEmailAddress(String emailAddress);

    boolean existsByUsername(String username);
}
