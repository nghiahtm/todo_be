package com.nghia.todolist.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nghia.todolist.dto.request.user.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nghia.todolist.dto.request.user.UserDto;
import com.nghia.todolist.dto.request.user.UserRequestRemove;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String createUser(CreateUserDto userRequestDto) {
        if (userRequestDto == null) {
            return "Invalid user data";
        }

        UserEntity userEntity = userMapper.toEntity(new UserDto(null,
                userRequestDto.getFullName(),
                userRequestDto.getEmail(),
                passwordEncoder.encode(userRequestDto.getPassword()),
                userRequestDto.getRole()));
        userRepository.save(userEntity);
        return "User created successfully";
    }

    public UserDtoNoPassword getUser(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userMapperNoPassword.toDto(userEntity.orElse(null));
    }

    public List<UserDtoNoPassword> getAllUser() {
        List<UserEntity> entities = userRepository.findAll();

        return entities.stream()
                .map(this.userMapperNoPassword::toDto)
                .collect(Collectors.toList());
    }

    public boolean removeUser(UserRequestRemove userRequestRemove) {
        // Logic to remove user by id and log the reason
        userRepository.deleteById(userRequestRemove.getId());
        return true;
    }

    private String getUserAuth() {
        // 1. Lấy đối tượng Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // 2. Lấy Principal (thông tin người dùng)
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Nếu Principal là UserDetails (thường thấy khi dùng UserDetailsService)
                username = ((UserDetails) principal).getUsername();
            } else {
                // Hoặc là một String (nếu là token)
                username = principal.toString();
            }
        }
        return username;
    }

    public Long getIdUserAuthorize(){
        UserDtoNoPassword detailUser = getUser(getUserAuth());
        return detailUser.getId();
    }
}
