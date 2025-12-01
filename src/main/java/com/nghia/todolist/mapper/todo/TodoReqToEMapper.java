package com.nghia.todolist.mapper.todo;

import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.entity.TodoEntity;
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
        return TodoEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .timeAt(dto.getTimeSet())
                .createDate(new Date())
                .updateAt(new Date())
                .build();
    }

    public TodoEntity toEntity(TodoRequest dto, Long idUser) {
        if(dto.getTimeSet() == null){
            dto.setTimeSet(new Date());
        }
        return TodoEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .timeAt(dto.getTimeSet())
                .idUser(idUser)
                .build();
    }
}
