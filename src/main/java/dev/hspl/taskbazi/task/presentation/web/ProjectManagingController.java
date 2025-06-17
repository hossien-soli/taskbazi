package dev.hspl.taskbazi.task.presentation.web;

import dev.hspl.taskbazi.common.domain.value.Description;
import dev.hspl.taskbazi.common.domain.value.UniversalUser;
import dev.hspl.taskbazi.common.presentation.web.ControllerUtils;
import dev.hspl.taskbazi.task.application.usage.write.RegisterProjectUseCase;
import dev.hspl.taskbazi.task.application.usage.write.StartProjectUseCase;
import dev.hspl.taskbazi.task.application.usage.write.cmd.RegisterProjectCommand;
import dev.hspl.taskbazi.task.application.usage.write.cmd.StartProjectCommand;
import dev.hspl.taskbazi.task.domain.value.ProjectId;
import dev.hspl.taskbazi.task.domain.value.ProjectTitle;
import dev.hspl.taskbazi.task.presentation.web.dto.RegisterProjectHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectManagingController extends ControllerUtils {
    private final RegisterProjectUseCase registerUseCase;
    private final StartProjectUseCase startUseCase;

    @PostMapping("/client/project/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void translateRegisterUseCase(
            Authentication authentication,
            @RequestBody RegisterProjectHttpRequest payload
    ) {
        UniversalUser authenticatedUser = validateAuthentication(authentication);

        var command = new RegisterProjectCommand(
                new ProjectTitle(payload.title()),
                payload.description() != null ? new Description(payload.description()) : null
        );

        registerUseCase.execute(authenticatedUser,command);
    }

    @PatchMapping("/client/project/{projectId}/start")
    @ResponseStatus(HttpStatus.OK)
    public void translateStartUseCase(
            Authentication authentication,
            @PathVariable String projectIdString, // we can use UUID directly here
            @RequestHeader(value = "If-Match",required = false) Integer entityVersion
    ) {
        ProjectId projectId = new ProjectId(validateUUIDString(projectIdString));
        var command = new StartProjectCommand(projectId,entityVersion);
        startUseCase.execute(validateAuthentication(authentication),command);
    }
}
