package com.nghia.todolist.entity;
import com.nghia.todolist.utils.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_users")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true,nullable=false)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
