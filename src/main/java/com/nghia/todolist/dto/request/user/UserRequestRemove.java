package com.nghia.todolist.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestRemove {
    private Long id;
    private String reason;
}
