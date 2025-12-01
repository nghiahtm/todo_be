package com.nghia.todolist.service;

import com.nghia.todolist.dto.request.user.AuthDto;
import com.nghia.todolist.dto.response.user.AuthResponse;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.RefreshTokenEntity;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.mapper.UserMapperNoPassword;
import com.nghia.todolist.repository.RefreshTokenRepository;
import com.nghia.todolist.repository.UserRepository;
import com.nghia.todolist.secure.jwt.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperNoPassword userMapperNoPassword;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public AuthResponse getUserByUserName(@Valid AuthDto login) {
        UserEntity userData = userRepository.findByEmail(login.getUsername()).orElse(null);
        assert userData != null;
        UserDtoNoPassword user = userMapperNoPassword.toDto(userData);
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        long expirationTime = jwtUtil.extractExpiration(accessToken).toInstant().toEpochMilli();
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .refreshToken(refreshToken)
                .userId(user.getId())
                .isRevoked(true)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
        userRepository.save(userData);
        return new AuthResponse(
                accessToken, refreshToken, expirationTime
        );
    }


    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UsernameNotFoundException("Invalid Token");
        }
        String userName = jwtUtil.extractUsername(refreshToken);
        UserEntity userEntity = userRepository.findByEmail(userName).orElseThrow(
                () -> new UsernameNotFoundException("Not Found User")
        );
        boolean isHasTokenRevoked = refreshTokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException("Not Found Refresh Token")
        ).stream().anyMatch((e)->e.getIsRevoked()==true);
        if(isHasTokenRevoked){
            String accessToken = jwtUtil.generateAccessToken(userMapperNoPassword.toDto(userEntity));
            long expirationTime = jwtUtil.extractExpiration(accessToken).toInstant().toEpochMilli();
            return new AuthResponse(
                    accessToken, refreshToken, expirationTime
            );
        }
        return null;
    }

    public boolean logout(String userName) {
        UserEntity userEntity = userRepository.findByEmail(userName).orElseThrow(
                () -> new UsernameNotFoundException("Not Found User")
        );
        List<RefreshTokenEntity> foundRefreshUser = refreshTokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException("Not Found Refresh Token")
        );
        foundRefreshUser.forEach((e)->e.setIsRevoked(false));
        refreshTokenRepository.saveAll(foundRefreshUser);
        return true;
    }
}
