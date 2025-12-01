package com.nghia.todolist.utils.enums;

import java.util.Optional;

public enum TodoStatus {
    SUCCESS,
    FAILED,
    ACTIVITY,
    STOP;

    public static Optional<TodoStatus> fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(TodoStatus.valueOf(value.toUpperCase().trim()));

        } catch (IllegalArgumentException e) {
            // Chuỗi không khớp với bất kỳ hằng số nào
            return Optional.empty();
        }
    }
}
