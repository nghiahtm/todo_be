package com.nghia.todolist.mapper;

import org.springframework.stereotype.Service;

import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.utils.base.BaseMapper;

@Service
public class UserMapperNoPassword extends  BaseMapper<UserEntity, UserDtoNoPassword> {
    @Override
    public UserDtoNoPassword toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDtoNoPassword(entity.getUserId(), entity.getName(), entity.getEmail(), entity.getRole());
    }

    @Override
    public UserEntity toEntity(UserDtoNoPassword dto) {
        
        return null;
    }
    
}
