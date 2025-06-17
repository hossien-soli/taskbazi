package dev.hspl.taskbazi.task.application.repository;

import dev.hspl.taskbazi.common.application.PaginationResult;
import dev.hspl.taskbazi.common.domain.value.UserId;
import dev.hspl.taskbazi.task.application.dto.read.MembershipProjectDTO;

// user is always a client -> role=CLIENT

public interface ProjectFetchRepository {
    PaginationResult<MembershipProjectDTO> fetchProjectsOfUser(
            UserId userId,
            short pageSize, // records per page(prevent large values if you get this size from user)
            short pageNumber,
            
    );
}
