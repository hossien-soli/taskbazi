package dev.hspl.taskbazi.task.application.service;

import dev.hspl.taskbazi.task.application.usage.read.FetchProjectUsersUseCase;
import dev.hspl.taskbazi.task.application.usage.read.FetchUserProjectsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // this readOnly flag is needed in the future if we want to replicate database for separating reads from writes!!!
public class ProjectFetchingApplicationService implements FetchUserProjectsUseCase, FetchProjectUsersUseCase {

}
