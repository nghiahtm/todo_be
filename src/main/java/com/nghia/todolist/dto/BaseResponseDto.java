package com.nghia.todolist.dto;
import java.util.List;

import com.nghia.todolist.entity.ErrorDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponseDto<T> {
    private int status;
    private String message;
    private T data;
    private long timestamp;
    private List<ErrorDetail> errors;

    static public <T> BaseResponseDto<T> success(int status, String message, T data, long timestamp) {
        return new BaseResponseDto<>(status, message, data, timestamp, null);
    }

    static public <T> BaseResponseDto<T> error(int status, String message, List<ErrorDetail> error, long timestamp) {
        return new BaseResponseDto<>(status, message, null, timestamp, error);
    }
}
