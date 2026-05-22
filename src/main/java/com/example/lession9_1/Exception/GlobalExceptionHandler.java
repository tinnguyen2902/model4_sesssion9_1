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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> map = new HashMap<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                map.put(fieldName, errorMessage);
            }
        }
        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setStatus("FAIL");
        response.setMessage("Dữ liệu không hợp lệ");
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleResourceNotFound(ResourceNotFoundException ex){
        Map<String, String> errorMap  = new HashMap<>();
        errorMap.put("message", ex.getMessage());

        ApiResponse<Map<String, String>> response = new ApiResponse<>(); // <-- Đã sửa
        response.setStatus("FAIL");
        response.setMessage(ex.getMessage());
        response.setData(errorMap);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleDuplicateResourceException(DuplicateResourceException ex){
        Map<String, String> errorMap  = new HashMap<>();
        errorMap.put("message", ex.getMessage());

        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setStatus("FAIL");
        response.setMessage(ex.getMessage());
        response.setData(errorMap);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}