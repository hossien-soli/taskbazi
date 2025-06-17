package dev.hspl.taskbazi.task.application.service;

import dev.hspl.taskbazi.task.application.usage.read.FetchProjectTasksUseCase;
import dev.hspl.taskbazi.task.application.usage.read.FetchUserTasksUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskFetchingApplicationService implements FetchUserTasksUseCase, FetchProjectTasksUseCase {

}
