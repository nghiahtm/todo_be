package com.nghia.todolist.controller.todo;

import com.nghia.todolist.dto.BaseResponseDto;
import com.nghia.todolist.dto.request.todo.TodoReqId;
import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.dto.request.todo.TodoUpdate;
import com.nghia.todolist.dto.response.BasePageResponse;
import com.nghia.todolist.dto.response.todo.TodoResponse;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.mapper.todo.TodoResToEMapper;
import com.nghia.todolist.service.TodoService;
import com.nghia.todolist.utils.enums.TodoStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    @PostMapping("/api/v1/todo/detail")
    BaseResponseDto<TodoResponse> getDetail(@RequestBody TodoReqId request) {
        TodoEntity todo = todoService.read(request.getId());
        return BaseResponseDto.success(
                200, "Successful", todoResToEMapper.toDto(todo), System.currentTimeMillis()
        );
    }

    @PostMapping("/api/v1/todo/getTodos")
    BaseResponseDto<BasePageResponse<TodoEntity>> getTodosFilter(
            @PageableDefault(sort = "createDate", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) TodoStatus status
    ) {
        Page<TodoEntity> todos = todoService.getUserTodo(title,startDate,endDate,status,pageable);

        return BaseResponseDto.success(
                200, "Successful",  new BasePageResponse<>(todos), System.currentTimeMillis()
        );
    }

    @PostMapping("/api/v1/todo/update")
    BaseResponseDto<TodoResponse> update(
            @RequestBody TodoUpdate todoUpdate
    ) {
        TodoRequest todoRequest = TodoRequest.builder()
                .description(todoUpdate.getDescription())
                .todoStatus(todoUpdate.getStatus())
                .timeSet(todoUpdate.getTimeSet())
                .title(todoUpdate.getTitle())
                .build();
        TodoEntity todo = todoService.update(todoUpdate.getId(),todoRequest);
        return BaseResponseDto.success(
                200, "Updated Successful",  todoResToEMapper.toDto(todo), System.currentTimeMillis()
        );
    }
}

