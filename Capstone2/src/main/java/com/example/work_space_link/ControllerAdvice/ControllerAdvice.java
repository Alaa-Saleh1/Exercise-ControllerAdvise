package com.example.work_space_link.ControllerAdvice;

import com.example.work_space_link.ApiResponse.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException exaption){
        String message= exaption.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }


    @ExceptionHandler(value =NullPointerException.class)
    public ResponseEntity NullPointerException(NullPointerException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException(NoResourceFoundException exception){
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(message);
    }
}
