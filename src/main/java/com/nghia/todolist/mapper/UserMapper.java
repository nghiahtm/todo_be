package com.nghia.todolist.mapper;
import org.springframework.stereotype.Service;

import com.nghia.todolist.dto.request.user.UserDto;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.utils.base.BaseMapper;



@Service
public class UserMapper extends  BaseMapper<UserEntity, UserDto> {
    @Override
    public UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(entity.getUserId(), entity.getName(), entity.getEmail(), entity.getPassword(), entity.getRole());
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return new UserEntity(
            dto.getId(), dto.getName(), dto.getEmail(),
            dto.getPassword(),
            dto.getRole(),
                null
        );   
    }
}
