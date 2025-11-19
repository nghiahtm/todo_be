package com.nghia.todolist.service;

import com.nghia.todolist.dto.request.user.AuthDto;
import com.nghia.todolist.dto.response.user.AuthResponse;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.mapper.UserMapperNoPassword;
import com.nghia.todolist.repository.UserRepository;
import com.nghia.todolist.secure.jwt.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperNoPassword userMapperNoPassword;

    @Autowired
    private JwtUtil jwtUtil;


    public AuthResponse getUserByUserName(@Valid  AuthDto login) {
        UserEntity userData = userRepository.findByEmail(login.getUsername()).orElse(null);
        UserDtoNoPassword user = userMapperNoPassword.toDto(userData);
        String accessToken = jwtUtil.generateToken(user);
        long expirationTime = jwtUtil.extractExpiration(accessToken).toInstant().toEpochMilli();
        return new AuthResponse(
                accessToken,expirationTime
        );
    }
}
