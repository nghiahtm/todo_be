package com.nghia.todolist.service;

import com.nghia.todolist.dto.request.todo.TodoRequest;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.entity.UserEntity;
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
        return todoReqToEMapper.toEntity(request, userService.getIdUserAuthorize());
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

    public Page<TodoEntity> getUserTodo(String title, Date startDate, Date endDate, TodoStatus status, Pageable pageable) {
        // 1. Kiểm tra Business Logic
        if (endDate != null && startDate != null) {
            // Giả sử getTimeAt là End Date và getCreateDate là Create Date
            if (endDate.before(startDate)) {
                // Ném ngoại lệ, Spring sẽ bắt ngoại lệ này và trả về lỗi 400 Bad Request
                throw new IllegalArgumentException("End date cannot be before the start date/creation date.");
            }
        }
        Long idUser = userService.getIdUserAuthorize();
        Specification<TodoEntity> specsTodo = TodoSpecifications.filterTodos(title, startDate, endDate, idUser, status);
        return todoRepository.findAll(specsTodo, pageable);
    }
}
