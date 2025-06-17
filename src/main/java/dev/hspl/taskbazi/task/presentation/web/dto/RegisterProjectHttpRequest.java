package dev.hspl.taskbazi.task.presentation.web.dto;

public record RegisterProjectHttpRequest(
        String title,
        String description // nullable - TODO: should be checked for Cross-Site Scripting Attacks!!
) {
}
