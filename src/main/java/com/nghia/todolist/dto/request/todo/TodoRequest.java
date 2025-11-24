package com.nghia.todolist.dto.request.todo;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class TodoRequest {
    private String description;
    @NotBlank(message = "Title not Empty")
    private String title;
    private Date timeSet;
}
