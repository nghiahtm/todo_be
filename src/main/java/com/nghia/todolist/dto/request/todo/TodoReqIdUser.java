package com.nghia.todolist.dto.request.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TodoReqIdUser {
    private Long id;
}
