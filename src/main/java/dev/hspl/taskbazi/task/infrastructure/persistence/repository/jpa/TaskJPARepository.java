package dev.hspl.taskbazi.task.infrastructure.persistence.repository.jpa;

import dev.hspl.taskbazi.task.infrastructure.persistence.entity.TaskJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TaskJPARepository extends JpaRepository<TaskJPAEntity, UUID> {
    @Modifying
    @Query("UPDATE Task t SET t.assignedByUserFullName = :newFullName WHERE t.assignedByUserId = :userId")
    void updateAssignedByUserFullName(
            @Param("userId") UUID userId,
            @Param("newFullName") String newFullName
    ); // int

    @Modifying
    @Query("UPDATE Task t SET t.assignedToUserFullName = :newFullName WHERE t.assignedToUserId = :userId")
    void updateAssignedToUserFullName(
            @Param("userId") UUID userId,
            @Param("newFullName") String newFullName
    ); // int

    // TODO: update both (assignedByUser/assignedToUser) in a single query using native SQL CASE ... WHEN ... THEN ... ELSE statements
}
