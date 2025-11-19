package com.nghia.todolist.dto.request.todo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long id;
    private String title;

    @Column(length = 500)
    private String description;

    private Date timeAt;

    private Date createDate;
    private Date updateAt;
}
