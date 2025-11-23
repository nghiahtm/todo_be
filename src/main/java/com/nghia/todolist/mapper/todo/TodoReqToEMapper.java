package com.nghia.todolist.mapper.todo;

import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.utils.base.BaseMapper;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class TodoReqToEMapper extends BaseMapper<TodoEntity, TodoRequest> {
    @Override
    public TodoRequest toDto(TodoEntity entity) {
        return null;
    }

    @Override
    public TodoEntity toEntity(TodoRequest dto) {
        return new TodoEntity(
                null,
                dto.getTitle(),
                null,
                dto.getDescription(),
                null,
                dto.getTimeSet(),
                new Date(),
                new Date()
        );
    }
}
