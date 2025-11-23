package com.nghia.todolist.dto.request.todo;

import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.utils.enums.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder

public class TodoRequest {
    private String description;
    @NotBlank(message = "Title not Empty")
    private String title;
    private Date timeSet;
    private Long idUserFound;
    @Builder.Default
    private TodoStatus todoStatus = TodoStatus.ACTIVITY;
}
