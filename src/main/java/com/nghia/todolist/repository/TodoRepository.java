package com.nghia.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nghia.todolist.entity.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
//    @Query("SELECT tbl_todo FROM " +
//            "TodoEntity t WHERE t.id IN " +
//            "(SELECT f.todoId FROM Follow f WHERE f.userId = :userId)")
//    List<TodoEntity> findFollowUser (Long idUser);
}
