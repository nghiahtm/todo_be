package com.nghia.todolist.service;

import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.mapper.UserMapperNoPassword;
import com.nghia.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperNoPassword userMapperNoPassword;

    public UserDtoNoPassword getUserByUserName(String email) {
        Optional<UserEntity> optionUser = userRepository.findByEmail(email);
        return optionUser.stream().findFirst().map(userMapperNoPassword::toDto).orElse(null);

    }
}
