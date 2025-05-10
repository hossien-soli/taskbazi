package dev.hspl.taskbazi.common.application;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime currentDateTime();
}
