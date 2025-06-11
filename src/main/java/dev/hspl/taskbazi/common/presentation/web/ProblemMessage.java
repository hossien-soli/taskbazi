package dev.hspl.taskbazi.common.presentation.web;

import org.springframework.lang.Nullable;

import java.util.Map;

public record ProblemMessage(
        String message,
        @Nullable Map<String, Object> data
) {
}
