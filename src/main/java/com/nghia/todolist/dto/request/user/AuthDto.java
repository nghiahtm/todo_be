package com.nghia.todolist.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDto {
    @NotBlank(message = "Name is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;

}
