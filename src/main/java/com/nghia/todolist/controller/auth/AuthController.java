package com.nghia.todolist.controller.auth;

import com.nghia.todolist.dto.BaseResponseDto;
import com.nghia.todolist.dto.request.user.AuthDto;
import com.nghia.todolist.dto.request.user.CreateUserDto;
import com.nghia.todolist.dto.response.user.AuthResponse;
import com.nghia.todolist.secure.jwt.JwtUtil;
import com.nghia.todolist.service.AuthService;
import com.nghia.todolist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/auth/login")
    public BaseResponseDto<AuthResponse> loginUser(
           @Valid @RequestBody AuthDto auth) {
        AuthResponse authResponse = authService.getUserByUserName(auth);
        return BaseResponseDto.success(
                200, "Login successful", authResponse, System.currentTimeMillis()
        );
    }

    @PostMapping("/api/v1/auth/create")
    public BaseResponseDto<String> createUser(@Valid @RequestBody CreateUserDto createUserDto) {

        String mess = userService.createUser(createUserDto);
        return BaseResponseDto.success(
                200, mess, null, System.currentTimeMillis()
        );
    }
}
