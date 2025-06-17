package dev.hspl.taskbazi.task.application.usage.read.query;

import dev.hspl.taskbazi.task.domain.value.ProjectStatus;

public record FetchUserProjectsQuery(
        String titleToMatch, // nullable / null=no filter by title
        ProjectStatus statusToMatch, // nullable / null=no filter by status
        boolean onlyOwnedProjects,
        boolean ascendingOrder // default is descending order by date of join to the project(joinedAt)
) {
}
