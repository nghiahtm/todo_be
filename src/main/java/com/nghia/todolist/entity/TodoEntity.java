package com.nghia.todolist.entity;

import com.nghia.todolist.utils.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_todo")
@Builder
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;
    private String title;

    private Long idUser;
    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TodoStatus todoStatus = TodoStatus.ACTIVITY;
    @Builder.Default
    private Date timeAt = new Date();
    @Builder.Default
    private Date createDate  = new Date();
    @Builder.Default
    private Date updateAt = new Date();
}
