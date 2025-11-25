package com.nghia.todolist.dto.request;

import com.nghia.todolist.utils.enums.TodoStatus;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Data
public class FilterRequestDto {
    private String title;
    private Date startDate;
    private Date endDate;
    private TodoStatus status;
    private Pageable pageable;
}
