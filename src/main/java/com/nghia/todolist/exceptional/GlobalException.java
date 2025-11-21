package com.nghia.todolist.exceptional;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nghia.todolist.dto.BaseResponseDto;
import com.nghia.todolist.entity.ErrorDetail;


@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleBaseException(BaseException ex) {
        ErrorDetail error = new ErrorDetail(
                ex.getMessage(),
                ex.getStatus().value()
        );
        return ResponseEntity.status(ex.getStatus()).body(
            BaseResponseDto.error(
                ex.getStatus().value(),
                ex.getMessage(),
                List.of(error),
                System.currentTimeMillis()      
        )); 
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleOther(Exception ex) {
        ErrorDetail error = new ErrorDetail(
                "Internal server error",    
                500
        );
        return ResponseEntity.internalServerError().body(error);
    }

     // ✅ Bắt lỗi validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> {
                    String message = error.getDefaultMessage();
                    return new ErrorDetail(message, HttpStatus.BAD_REQUEST.value());
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                BaseResponseDto.error(
                        400,
                        "Validation failed",
                        errors,
                        System.currentTimeMillis()
                )
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleJwtException(JwtException ex) {

        return ResponseEntity.badRequest().body(
                BaseResponseDto.error(
                        403,
                        "Authentication failed",
                        List.of(new ErrorDetail(
                                "Token in valid",
                                403
                        )),
                        System.currentTimeMillis()
                )
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleJwtException(RuntimeException ex) {

        return ResponseEntity.badRequest().body(
                BaseResponseDto.error(
                        HttpServletResponse.SC_BAD_REQUEST,
                        " failed",
                        List.of(new ErrorDetail(
                                "BAD REQUEST",
                                HttpServletResponse.SC_BAD_REQUEST
                        )),
                        System.currentTimeMillis()
                )
        );
    }
}
