package com.nghia.todolist.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestRemove {
    @NotBlank(message = "ID is required")
    private Long id;
    @NotBlank(message = "Reason is required")
    private String reason;
}
