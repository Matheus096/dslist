package com.devsuperior.dslist.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        
        // Percorre a lista de erros gerada pelo Spring Validation
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationError(f.getField(), f.getDefaultMessage()));
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
