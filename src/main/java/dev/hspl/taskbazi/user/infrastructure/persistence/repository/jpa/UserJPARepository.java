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
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role = :userRole")
    Optional<UserJPAEntity> findRoleByUsername(
            @Param("userRole") UserRole userRole,
            @Param("username") String username
    );

    @Query("SELECT u FROM User u WHERE u.emailAddress = :emailAddress AND u.role = :userRole")
    Optional<UserJPAEntity> findRoleByEmailAddress(
            @Param("userRole") UserRole userRole,
            @Param("emailAddress") String emailAddress
    );

    boolean existsByEmailAddress(String emailAddress);

    boolean existsByUsername(String username);
}
