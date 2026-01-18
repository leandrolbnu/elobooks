package com.elotech.elobooks.handler;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.elotech.elobooks.exception.DuplicateResourceException;
import com.elotech.elobooks.exception.NotFoundException;

@ControllerAdvice
public class ElobooksExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ElobooksError> notFoundException(NotFoundException e) {
        ElobooksError error = new ElobooksError(
                e.getMessage(),
                e.toString(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now().toString()
        );

        return ((BodyBuilder) ResponseEntity.notFound()).body(error);
    }
    
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ElobooksError> duplicateEmailException(DuplicateResourceException e) {
        ElobooksError error = new ElobooksError(
                e.getMessage(),
                e.toString(),
                HttpStatus.UNPROCESSABLE_CONTENT,
                LocalDateTime.now().toString()
        );

        return ResponseEntity.unprocessableContent().body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ElobooksError> argumentNotValidException(MethodArgumentNotValidException e) {
        ElobooksError error = new ElobooksError(
                e.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
                e.toString(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now().toString()
        );

        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ElobooksError> illegalArgumentException(IllegalArgumentException e) {
        ElobooksError error = new ElobooksError(
                e.getMessage(),
                e.toString(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now().toString()
        );

        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ElobooksError> dataIntegrityViolationException(DataIntegrityViolationException e) {
        ElobooksError error = new ElobooksError(
                e.getMessage(),
                e.toString(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now().toString()
        );

        return ResponseEntity.badRequest().body(error);
    }
    
}
