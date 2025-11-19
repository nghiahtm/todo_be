package com.nghia.todolist.service;

import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthUserDetailService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    public AuthUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                userData.getEmail(),
                userData.getPassword(),
                List.of(new SimpleGrantedAuthority(userData.getRole().name())) // role từ entity
        );
    }
}
