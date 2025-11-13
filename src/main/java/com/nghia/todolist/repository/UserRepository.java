package com.nghia.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nghia.todolist.entity.UserEntity;


@Repository
public interface  UserRepository extends JpaRepository<UserEntity, Long> {
   
}
