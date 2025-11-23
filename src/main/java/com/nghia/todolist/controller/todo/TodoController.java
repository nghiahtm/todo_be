package com.nghia.todolist.controller.todo;

import com.nghia.todolist.dto.BaseResponseDto;
import com.nghia.todolist.dto.request.todo.TodoReqId;
import com.nghia.todolist.dto.request.todo.TodoReqIdUser;
import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.dto.response.todo.TodoResponse;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.mapper.todo.TodoResToEMapper;
import com.nghia.todolist.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoResToEMapper todoResToEMapper;

    @PostMapping("/api/v1/todo/create")
    BaseResponseDto<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request) {
        TodoEntity todo = todoService.create(request);
        return BaseResponseDto.success(
                200, "Created Successfully", todoResToEMapper.toDto(todo), System.currentTimeMillis()
        );
    }

    @PostMapping("/api/v1/todo/getTodo")
    BaseResponseDto<TodoResponse> getTodo(@RequestBody TodoReqId request) {
        TodoEntity todo = todoService.read(request.getId());
        return BaseResponseDto.success(
                200, "Successful", todoResToEMapper.toDto(todo), System.currentTimeMillis()
        );
    }

    @PostMapping("/api/v1/todo/getTodos")
    BaseResponseDto<List<TodoResponse>> getTodo(@RequestBody TodoReqIdUser request) {
        List<TodoEntity> todos = todoService.getUserTodo(request.getId());
        return BaseResponseDto.success(
                200, "Successful", todos.stream().map((e) -> todoResToEMapper.toDto(e)).toList(), System.currentTimeMillis()
        );
    }
}

