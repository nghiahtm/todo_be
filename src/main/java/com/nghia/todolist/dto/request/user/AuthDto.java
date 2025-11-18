package com.nghia.todolist.dto.request.user;

import com.nghia.todolist.utils.custom_annotation.account_not_exited.ValidAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ValidAccount
public class AuthDto {
    @NotBlank(message = "Name is required")
    @Size(min = 8,max = 20)
    @Email(message = "Email is not valid")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8,max = 20)
    private String password;
}
