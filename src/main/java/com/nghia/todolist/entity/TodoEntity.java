package com.nghia.todolist.entity;

import com.nghia.todolist.utils.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;
    private String title;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    private Date timeAt;
    private Date createDate;
    private Date updateAt;
}
