package com.nghia.todolist.dto.request.todo;
import com.nghia.todolist.utils.enums.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdate {
    private Long id;
    private TodoStatus status;
    private String description;
    @NotBlank(message = "Title not Empty")
    private String title;
    private Date timeSet;
}
