package com.nghia.todolist.dto.request.user;
import com.nghia.todolist.utils.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}

