package com.nghia.todolist.dto.response.user;

import com.nghia.todolist.utils.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDtoNoPassword {
    private Long id;
    private String name;
    private String email;
    private Role role;
}

