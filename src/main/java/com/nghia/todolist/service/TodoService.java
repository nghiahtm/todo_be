package com.nghia.todolist.service;
import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.entity.UserEntity;
import com.nghia.todolist.mapper.todo.TodoReqToEMapper;
import com.nghia.todolist.repository.TodoRepository;
import com.nghia.todolist.repository.UserRepository;
import com.nghia.todolist.utils.base.BaseCRUDInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService implements BaseCRUDInterface<TodoRequest, TodoEntity> {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoReqToEMapper todoReqToEMapper;

    @Autowired
    private UserService userService;

    @Override
    public TodoEntity create(TodoRequest request) {

        TodoEntity todoEntity = todoReqToEMapper.toEntity(request, userService.getIdUserAuthorize());
        todoRepository.save(todoEntity);
        return todoReqToEMapper.toEntity(request,userService.getIdUserAuthorize());
    }

    @Override
    public TodoEntity read(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public TodoEntity update(TodoRequest request) {
        return null;
    }

    @Override
    public TodoEntity remove(Long id) {
         todoRepository.deleteById(id);
         return new TodoEntity();
    }

    public List<TodoEntity> getUserTodo() {
        Long idUser = userService.getIdUserAuthorize();
        return todoRepository.findByIdUserOrderByCreateDateDesc(idUser);
    }
}
