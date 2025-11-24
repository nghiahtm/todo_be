package com.nghia.todolist.mapper.todo;

import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.dto.response.todo.TodoResponse;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.utils.base.BaseMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoResToEMapper extends BaseMapper<TodoEntity, TodoResponse> {
    @Override
    public TodoResponse toDto(TodoEntity entity) {
        return TodoResponse.builder()
                .description(entity.getDescription())
                .title(entity.getTitle())
                .timeSet(entity.getTimeAt())
                .idUser(entity.getIdUser())
                .todoStatus(entity.getTodoStatus())
                .idTodo(entity.getTodoId())
                .createdAt(entity.getCreateDate())
                .updatedAt(entity.getUpdateAt())
                .build();
    }

    @Override
    public TodoEntity toEntity(TodoResponse dto) {
        return null;
    }
}
