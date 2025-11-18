package com.nghia.todolist.dto.request.user;

import com.nghia.todolist.entity.Role;
import com.nghia.todolist.utils.custom_annotation.email_exist.UniqueEmail;
import com.nghia.todolist.utils.custom_annotation.match_password.CustomPasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CustomPasswordMatch
public class CreateUserDto {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @UniqueEmail()
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8,max = 20)
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 8,max = 20)
    private String confirmPassword;

    private Role role;
}
