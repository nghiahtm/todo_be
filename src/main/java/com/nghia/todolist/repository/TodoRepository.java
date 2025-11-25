package com.nghia.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nghia.todolist.entity.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>, JpaSpecificationExecutor<TodoEntity> {
    List<TodoEntity> findByIdUserOrderByCreateDateDesc(Long idUser);
}
