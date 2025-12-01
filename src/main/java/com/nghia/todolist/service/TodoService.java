package com.nghia.todolist.service;

import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.mapper.todo.TodoReqToEMapper;
import com.nghia.todolist.repository.TodoRepository;
import com.nghia.todolist.repository.UserRepository;
import com.nghia.todolist.utils.base.BaseCRUDInterface;
import com.nghia.todolist.utils.enums.TodoStatus;
import com.nghia.todolist.utils.specifications.TodoSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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
        return todoReqToEMapper.toEntity(request, userService.getIdUserAuthorize());
    }

    @Override
    public TodoEntity read(Long id) {
        Long idUser = userService.getIdUserAuthorize();
        TodoEntity todoFound = todoRepository.findByTodoIdAndIdUser(id,idUser);
        if(todoFound == null||todoFound.getIsRemoved()){
            throw new NullPointerException("Todo Not Found");
        }
        return todoFound;
    }

    @Override
    public TodoEntity update(Long id,TodoRequest request) {
        TodoEntity existingTodo = read(id);
        if(!request.getDescription().isBlank()) {
            existingTodo.setDescription(request.getDescription());
        }
        if(!request.getTitle().isBlank()) {
            existingTodo.setDescription(request.getTitle());
        }
        if(request.getTodoStatus() != null) {
            existingTodo.setTodoStatus(request.getTodoStatus());
        }
        if(request.getTimeSet() != null) {
            existingTodo.setTimeAt(request.getTimeSet());
        }else{
            existingTodo.setTimeAt(new Date());
        }
        existingTodo.setUpdateAt(new Date());
        todoRepository.save(existingTodo);
        return read(id);
    }

    @Override
    public TodoEntity remove(Long id) {
        TodoEntity existingTodo = read(id);
        existingTodo.setIsRemoved(true);
        existingTodo.setTodoStatus(TodoStatus.STOP);
        todoRepository.save(existingTodo);
        return existingTodo;
    }

    public Page<TodoEntity> getUserTodo(String title, Date startDate, Date endDate, String status, Pageable pageable) {
        // 1. Kiểm tra Business Logic
        Long idUser = userService.getIdUserAuthorize();
        if (endDate != null && startDate != null) {
            // Giả sử getTimeAt là End Date và getCreateDate là Create Date
            if (endDate.before(startDate)) {
                // Ném ngoại lệ, Spring sẽ bắt ngoại lệ này và trả về lỗi 400 Bad Request
                throw new IllegalArgumentException("End date cannot be before the start date/creation date.");
            }
        }
        Optional<TodoStatus> inputStatus = TodoStatus.fromString(status);
        TodoStatus statusParse = null;
        if(inputStatus.isPresent()){
            statusParse = inputStatus.get();
        }
        Specification<TodoEntity> specsTodo = TodoSpecifications.filterTodos(title, startDate, endDate, idUser,
                statusParse);
        return todoRepository.findAll(specsTodo, pageable);
    }
}
