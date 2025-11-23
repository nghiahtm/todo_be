package com.nghia.todolist.dto.response.todo;
import com.nghia.todolist.utils.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TodoResponse {
    private String description;
    private String title;
    private Date timeSet;
    private Long idUser;
    private TodoStatus todoStatus;
    private Long idTodo;
}
