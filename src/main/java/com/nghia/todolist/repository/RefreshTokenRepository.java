package com.nghia.todolist.repository;

import com.nghia.todolist.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<List<RefreshTokenEntity>> findByUserId (Long userId);
}
