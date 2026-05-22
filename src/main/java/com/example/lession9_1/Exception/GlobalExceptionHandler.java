package com.example.lession9_1.Exception;

import com.example.lession9_1.Model.DTO.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// nơi xử lý lỗi cục bộ
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> map = new HashMap<>();
        // lấy ds lỗi dưới dạng oj
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        // duyệt qua từng lỗi
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                map.put(fieldName, errorMessage);
            }
        }
       // đóng gói ds lỗi
        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setStatus("FAIL");
        response.setMessage("Dữ liệu không hợp lệ");
        response.setData(map);
        // trả về kèm theo mã 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}