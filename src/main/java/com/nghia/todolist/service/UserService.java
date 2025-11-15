package com.nghia.todolist.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nghia.todolist.dto.request.user.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nghia.todolist.dto.request.user.UserDto;
import com.nghia.todolist.dto.request.user.UserRequestRemove;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.Role;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.mapper.UserMapper;
import com.nghia.todolist.mapper.UserMapperNoPassword;
import com.nghia.todolist.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperNoPassword userMapperNoPassword;

    public String createUser(CreateUserDto userRequestDto) {
        if (userRequestDto == null) {
            return "Invalid user data";
        }
        if(userRequestDto.getRole() == null) {
            userRequestDto.setRole(Role.USER);
        }
        UserEntity userEntity = userMapper.toEntity(new UserDto(null,
                userRequestDto.getFullName(),
                userRequestDto.getEmail(),
                userRequestDto.getPassword(),
                userRequestDto.getRole()));
        userRepository.save(userEntity);
        return "User created successfully";
    }

    public UserDtoNoPassword getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapperNoPassword::toDto).orElse(null);

    }

    public List<UserDtoNoPassword> getAllUser() {
        List<UserEntity> entities = userRepository.findAll();

        return entities.stream()
                .map(this.userMapperNoPassword::toDto)
                .collect(Collectors.toList());
    }

    public boolean removeUser(UserRequestRemove userRequestRemove) {

        if (userRequestRemove == null || userRequestRemove.getId() == null
                || userRequestRemove.getReason() == null) {
            return false;
        }

        // Logic to remove user by id and log the reason
        userRepository.deleteById(userRequestRemove.getId());
        return true;
    }

}
