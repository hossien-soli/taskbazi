package dev.hspl.taskbazi.common.application;

import java.util.List;

public record PaginationResult<T>(
        List<T> records,
        int countInPage,
        int currentPage,
        int lastPage,
        int totalRecords
) {
}
