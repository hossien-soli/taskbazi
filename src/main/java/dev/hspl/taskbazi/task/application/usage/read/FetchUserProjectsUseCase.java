package dev.hspl.taskbazi.task.application.usage.read;

import dev.hspl.taskbazi.common.application.PaginationResult;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.task.application.dto.read.MembershipProjectDTO;
import dev.hspl.taskbazi.task.application.usage.read.query.FetchUserProjectsQuery;
import org.springframework.lang.NonNull;

// fetch all projects of a user owned/joined

public interface FetchUserProjectsUseCase {
    PaginationResult<MembershipProjectDTO> execute(
            @NonNull UniversalUser authenticatedUser,
            @NonNull FetchUserProjectsQuery query
    );
}
